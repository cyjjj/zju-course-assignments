`include "define.vh"
/**
 * Controller for MIPS 5-stage pipelined CPU.
 */
module controller (/*AUTOARG*/
	input wire clk,  // main clock
	input wire rst,  // synchronous reset
	// debug
	`ifdef DEBUG
	input wire debug_en,  // debug enable
	input wire debug_step,  // debug step clock
	`endif
	// instruction decode
	input wire [31:0] inst,  // instruction
	output reg [2:0] pc_src,  // how would PC change to next
	output reg imm_ext,  // whether using sign extended to immediate data
	output reg [1:0] exe_a_src,  // data source of operand A for ALU
	output reg [1:0] exe_b_src,  // data source of operand B for ALU
	output reg [3:0] exe_alu_oper,  // ALU operation type
	output reg mem_ren,  // memory read enable signal
	output reg mem_wen,  // memory write enable signal
	output reg [1:0] wb_addr_src,  // address source to write data back to registers
	output reg wb_data_src,  // data source of data being written back to registers
	output reg wb_wen,  // register write enable signal
	output reg unrecognized,  // whether current instruction can not be recognized
	
	// debug control
	//output reg cpu_rst,  // cpu reset signal
	//output reg cpu_en  // cpu enable signal
	
	// pipeline control
	output reg if_rst,  // stage reset signal
	output reg if_en,  // stage enable signal
	input wire if_valid,  // stage valid flag
	output reg id_rst,
	output reg id_en,
	input wire id_valid,
	output reg exe_rst,
	output reg exe_en,
	input wire exe_valid,
	output reg mem_rst,
	output reg mem_en,
	input wire mem_valid,
	output reg wb_rst,
	output reg wb_en,
	input wire wb_valid,
	
	// stall control
	input wire is_branch,  // whether instruction in ID stage is jump/branch instruction
	input wire [4:0] regw_addr_exe,  // register write address from EXE stage
	input wire wb_wen_exe,  // register write enable signal feedback from EXE stage
	input wire [4:0] regw_addr_mem,  // register write address from MEM stage
	input wire wb_wen_mem,  // register write enable signal feedback from MEM stage
	// forward
	input wire mem_ren_exe,//used for forwarding(lw or not)
	input wire mem_ren_mem,//used for forwarding(lw or not)
	output reg [1:0] exe_fwd_a_ctrl,//用来选择forwarding path，4路选择;0:data_a_ld(reg),1:alu_out_exe,2:alu_out_mem,3:data_out_mem,
	output reg [1:0] exe_fwd_b_ctrl,
	output reg fwd_m,
	//is not taken
	input wire is_not_taken
	);
	
	`include "mips_define.vh"
	
	// stall control signals
	reg reg_stall;
	reg branch_stall;
	wire [4:0] id_rs, id_rt;
	reg rs_used, rt_used; //flags for registerd used or not 
	
	assign id_rs = inst[25:21], //rs
			 id_rt = inst[20:16]; //rt
			 
	reg is_store, is_load;
			 
	// instruction decode
	always @(*) begin
		pc_src = PC_NEXT;
		imm_ext = 0;
		exe_a_src = EXE_A_RS;
		exe_b_src = EXE_B_RT;
		exe_alu_oper = EXE_ALU_ADD;
		mem_ren = 0;
		mem_wen = 0;
		wb_addr_src = WB_ADDR_RD;
		wb_data_src = WB_DATA_ALU;
		wb_wen = 0;
		rs_used = 0;
		rt_used = 0;
		unrecognized = 0;
		is_load = 0;
		is_store = 0;
		case (inst[31:26]) // opcode
			INST_R: begin // r type
				wb_wen = 1;
				rs_used = 1;
				rt_used = 1;
				case (inst[5:0])
					R_FUNC_JR: begin
						pc_src = PC_JR;
						wb_wen = 0;
						rt_used = 0;
					end
					R_FUNC_ADD: begin
						exe_alu_oper = EXE_ALU_ADD;
						wb_addr_src = WB_ADDR_RD;
						wb_data_src = WB_DATA_ALU;
					end
					R_FUNC_SUB: begin
						exe_alu_oper = EXE_ALU_SUB;
						wb_addr_src = WB_ADDR_RD;
						wb_data_src = WB_DATA_ALU;
					end
					R_FUNC_AND: begin
						exe_alu_oper = EXE_ALU_AND;
						wb_addr_src = WB_ADDR_RD;
						wb_data_src = WB_DATA_ALU;
					end
					R_FUNC_OR: begin
						exe_alu_oper = EXE_ALU_OR;
						wb_addr_src = WB_ADDR_RD;
						wb_data_src = WB_DATA_ALU;
					end
					R_FUNC_SLT: begin
						exe_alu_oper = EXE_ALU_SLT;
						wb_addr_src = WB_ADDR_RD;
						wb_data_src = WB_DATA_ALU;
					end
					default: begin
						unrecognized = 1;
						wb_wen = 0;
						rs_used = 0;
						rt_used = 0;
					end
				endcase 
			end
			INST_J: begin
				pc_src = PC_JUMP;
			end
			INST_JAL: begin
				pc_src = PC_JUMP;
				exe_a_src = EXE_A_LINK;
				exe_b_src = EXE_B_LINK;
				exe_alu_oper = EXE_ALU_ADD;
				wb_addr_src = WB_ADDR_LINK;
				wb_data_src = WB_DATA_ALU;
				wb_wen = 1;
			end
			INST_BEQ: begin
				imm_ext = 1; // signed extension
				pc_src = PC_BEQ;
				exe_a_src = EXE_A_BRANCH;
				exe_b_src = EXE_B_BRANCH;
				exe_alu_oper = EXE_ALU_ADD;
				rs_used = 1;
				rt_used = 1;
			end
			INST_BNE: begin
				imm_ext = 1; // signed extension
				pc_src = PC_BNE;
				exe_a_src = EXE_A_BRANCH;
				exe_b_src = EXE_B_BRANCH;
				exe_alu_oper = EXE_ALU_ADD;
				rs_used = 1;
				rt_used = 1;
			end
			INST_ADDI: begin
				imm_ext = 1;
				exe_b_src = EXE_B_IMM;
				exe_alu_oper = EXE_ALU_ADD;
				wb_addr_src = WB_ADDR_RT;
				wb_data_src = WB_DATA_ALU;
				wb_wen = 1;
				rs_used = 1;
			end
			INST_ANDI: begin
				imm_ext = 1;
				exe_b_src = EXE_B_IMM;
				exe_alu_oper = EXE_ALU_AND;
				wb_addr_src = WB_ADDR_RT;
				wb_data_src = WB_DATA_ALU;
				wb_wen = 1;
				rs_used = 1;
			end
			INST_ORI: begin
				imm_ext = 0;
				exe_b_src = EXE_B_IMM;
				exe_alu_oper = EXE_ALU_OR;
				wb_addr_src = WB_ADDR_RT;
				wb_data_src = WB_DATA_ALU;
				wb_wen = 1;
				rs_used = 1;
			end
			INST_LW: begin
				imm_ext = 1;
				exe_b_src = EXE_B_IMM;
				exe_alu_oper = EXE_ALU_ADD;
				mem_ren = 1;
				wb_addr_src = WB_ADDR_RT;
				wb_data_src = WB_DATA_MEM;
				wb_wen = 1;
				rs_used = 1;
				is_load = 1;
				is_store = 0;
			end
			INST_SW: begin
				imm_ext = 1;
				exe_b_src = EXE_B_IMM;
				exe_alu_oper = EXE_ALU_ADD;
				mem_wen = 1;
				rs_used = 1;
				rt_used = 1;
				is_load = 0;
				is_store = 1;
			end
			default: begin
				unrecognized = 1;
			end
		endcase
	end
	
	//-----------------------------------------------------------------
	// stall control & forward control
	
	//forwarding path，4路选择
	//0:data_a_ld(最初的选择),1:alu_out_exe,2:alu_out_mem,3:data_out_mem
	always @(*) begin//在ID阶段，判断什么时候需要stall
		reg_stall = 0;
		fwd_m = 0;
		exe_fwd_a_ctrl = 0;
		exe_fwd_b_ctrl = 0;//默认情况选择reg中输出
		
		if (rs_used && id_rs != 0) begin
			if (regw_addr_exe == id_rs && wb_wen_exe) begin
				if(mem_ren_exe) //exe is lw
				    reg_stall = 1;
				else exe_fwd_a_ctrl = 2'b01; //alu_out_exe
			end
			else if (regw_addr_mem == id_rs && wb_wen_mem) begin
				if(mem_ren_mem) //mem is lw
				   exe_fwd_a_ctrl = 2'b11; //data_out_mem
				else exe_fwd_a_ctrl = 2'b10; //alu_out_mem
			end
		end
		
		if (rt_used && id_rt != 0) begin
			if (regw_addr_exe == id_rt && wb_wen_exe) begin
				if(mem_ren_exe && inst[31:26] != INST_SW)
				    reg_stall = 1;
				else if(~mem_ren_exe && inst[31:26] != INST_SW)
				    exe_fwd_b_ctrl = 2'b01; //alu_out_exe
				else 
				    fwd_m = 1;
			end
			else if (regw_addr_mem == id_rt && wb_wen_mem) begin
				if(mem_ren_mem)
				   exe_fwd_b_ctrl = 2'b11; //data_out_mem
				else if(inst[31:26] != INST_SW)
				   exe_fwd_b_ctrl = 2'b10; //alu_out_mem
			end
		end
	end
	
	always @(*) begin
		branch_stall = 0;
		if (is_branch && ~is_not_taken) begin
		//is branch and take the branch --- 1 stall
			branch_stall = 1;
		end
	end
	
	// debug control
	`ifdef DEBUG
	reg debug_step_prev;
	
	always @(posedge clk) begin
		debug_step_prev <= debug_step;
	end
	`endif
	
	always @(*) begin
		//cpu_rst = 0;
		//cpu_en = 1;
		//if (rst) begin
		//	cpu_rst = 1;
		//end
		if_rst = 0;
		if_en = 1;
		id_rst = 0;
		id_en = 1;
		exe_rst = 0;
		exe_en = 1;
		mem_rst = 0;
		mem_en = 1;
		wb_rst = 0;
		wb_en = 1;
		if (rst) begin
			if_rst = 1;
			id_rst = 1;
			exe_rst = 1;
			mem_rst = 1;
			wb_rst = 1;
		end
		`ifdef DEBUG
		// suspend and step execution
		else if ((debug_en) && ~(~debug_step_prev && debug_step)) begin
			//cpu_en = 0;
			if_en = 0;
			id_en = 0;
			exe_en = 0;
			mem_en = 0;
			wb_en = 0;
		end
		`endif
		// stall
		else if (reg_stall) begin
			if_en = 0;
			id_en = 0;
			exe_rst = 1;
		end
		else if (branch_stall) begin
			id_rst = 1;
		end
	end
	
endmodule
