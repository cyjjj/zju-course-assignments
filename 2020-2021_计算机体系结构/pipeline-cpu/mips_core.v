`include "define.vh"
/**
 * MIPS 5-stage pipeline CPU Core, including data path and co-processors.
 */
module mips_core (
	input wire clk,  // main clock
	input wire rst,  // synchronous reset
	// debug
	`ifdef DEBUG
	input wire debug_en,  // debug enable
	input wire debug_step,  // debug step clock
	input wire [6:0] debug_addr,  // debug address
	output wire [31:0] debug_data,  // debug data
	`endif
	// instruction interfaces
	output wire inst_ren,  // instruction read enable signal 
	output wire [31:0] inst_addr,  // address of instruction needed /* PC_out */
	input wire [31:0] inst_data,  // instruction fetched /* inst_in */
	// memory interfaces
	output wire mem_ren,  // memory read enable signal
	output wire mem_wen,  // memory write enable signal / * mem_w = mem_wen&&(~mem_ren) */
	output wire [31:0] mem_addr,  // address of memory /* Addr_out */
	output wire [31:0] mem_dout,  // data writing to memory /* Data_out */
	input wire [31:0] mem_din  // data read from memory / * Data_in */
	);
	
	// control signals
	wire [31:0] inst_data_ctrl;
	
	wire [2:0] pc_src_ctrl;
	wire imm_ext_ctrl;
	wire [1:0] exe_a_src_ctrl, exe_b_src_ctrl;
	wire [3:0] exe_alu_oper_ctrl;
	wire mem_ren_ctrl;
	wire mem_wen_ctrl;
	wire [1:0] wb_addr_src_ctrl;
	wire wb_data_src_ctrl;
	wire wb_wen_ctrl;
	
	//wire cpu_rst, cpu_en;
	wire if_rst, if_en, if_valid;
	wire id_rst, id_en, id_valid;
	wire exe_rst, exe_en, exe_valid;
	wire mem_rst, mem_en, mem_valid;
	wire wb_rst, wb_en, wb_valid;
	
	// stall
	wire is_branch;
	wire [4:0] regw_addr_exe, regw_addr_mem;
	wire wb_wen_exe, wb_wen_mem;
	
	//forward
	wire [1:0] exe_fwd_a_ctrl, exe_fwd_b_ctrl;
	wire mem_ren_exe, mem_ren_mem;
	wire is_lw_sw_forward_rs, is_lw_sw_forward_rt;
	wire fwd_m;
	
	//is not taken
	wire is_not_taken;
	
	// controller
	controller CONTROLLER (
		.clk(clk),
		.rst(rst),
		`ifdef DEBUG
		.debug_en(debug_en),
		.debug_step(debug_step),
		`endif
		.inst(inst_data_ctrl),
		.pc_src(pc_src_ctrl),
		.imm_ext(imm_ext_ctrl),
		.exe_a_src(exe_a_src_ctrl),
		.exe_b_src(exe_b_src_ctrl),
		.exe_alu_oper(exe_alu_oper_ctrl),
		.mem_ren(mem_ren_ctrl),
		.mem_wen(mem_wen_ctrl),
		.wb_addr_src(wb_addr_src_ctrl),
		.wb_data_src(wb_data_src_ctrl),
		.wb_wen(wb_wen_ctrl),
		.unrecognized(),
		//.cpu_rst(cpu_rst),
		//.cpu_en(cpu_en)
		.if_rst(if_rst),
		.if_en(if_en),
		.if_valid(if_valid),
		.id_rst(id_rst),
		.id_en(id_en),
		.id_valid(id_valid),
		.exe_rst(exe_rst),
		.exe_en(exe_en),
		.exe_valid(exe_valid),
		.mem_rst(mem_rst),
		.mem_en(mem_en),
		.mem_valid(mem_valid),
		.wb_rst(wb_rst),
		.wb_en(wb_en),
		.wb_valid(wb_valid),
		// stall
		.is_branch(is_branch),
		.regw_addr_exe(regw_addr_exe),
		.wb_wen_exe(wb_wen_exe),
		.regw_addr_mem(regw_addr_mem),
		.wb_wen_mem(wb_wen_mem),
		//forward
		.mem_ren_exe(mem_ren_exe),
		.mem_ren_mem(mem_ren_mem),
		.exe_fwd_a_ctrl(exe_fwd_a_ctrl),
		.exe_fwd_b_ctrl(exe_fwd_b_ctrl),
		.fwd_m(fwd_m),
		//is not taken
		.is_not_taken(is_not_taken)
	);
	
	// data path
	datapath DATAPATH (
		.clk(clk),
		`ifdef DEBUG
		.debug_addr(debug_addr[5:0]),
		.debug_data(debug_data),
		`endif
		.inst_data_id(inst_data_ctrl),
		.pc_src_ctrl(pc_src_ctrl),
		.imm_ext_ctrl(imm_ext_ctrl),
		.exe_a_src_ctrl(exe_a_src_ctrl),
		.exe_b_src_ctrl(exe_b_src_ctrl),
		.exe_alu_oper_ctrl(exe_alu_oper_ctrl),
		.mem_ren_ctrl(mem_ren_ctrl),
		.mem_wen_ctrl(mem_wen_ctrl),
		.wb_addr_src_ctrl(wb_addr_src_ctrl),
		.wb_data_src_ctrl(wb_data_src_ctrl),
		.wb_wen_ctrl(wb_wen_ctrl),
		.inst_ren(inst_ren),
		.inst_addr(inst_addr),
		.inst_data(inst_data),
		.mem_ren(mem_ren),
		.mem_wen(mem_wen),
		.mem_addr(mem_addr),
		.mem_dout(mem_dout),
		.mem_din(mem_din),
		//.cpu_rst(cpu_rst),
		//.cpu_en(cpu_en)
		.if_rst(if_rst),
		.if_en(if_en),
		.if_valid(if_valid),
		.id_rst(id_rst),
		.id_en(id_en),
		.id_valid(id_valid),
		.exe_rst(exe_rst),
		.exe_en(exe_en),
		.exe_valid(exe_valid),
		.mem_rst(mem_rst),
		.mem_en(mem_en),
		.mem_valid(mem_valid),
		.wb_rst(wb_rst),
		.wb_en(wb_en),
		.wb_valid(wb_valid),
		// stall
		.is_branch(is_branch),
		.regw_addr_exe(regw_addr_exe),
		.wb_wen_exe(wb_wen_exe),
		.regw_addr_mem(regw_addr_mem),
		.wb_wen_mem(wb_wen_mem),
		//forward
		.mem_ren_exe(mem_ren_exe),
		.mem_ren_mem(mem_ren_mem),
		.exe_fwd_a_ctrl(exe_fwd_a_ctrl),
		.exe_fwd_b_ctrl(exe_fwd_b_ctrl),
		.fwd_m(fwd_m),
		//is not taken
		.is_not_taken(is_not_taken)
	);
	
endmodule
