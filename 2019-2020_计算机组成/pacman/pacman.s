.text 0x0000
//中断向量区
j start0; //reset
add $zero,$zero,$zero; //00000004
add $zero,$zero,$zero; //00000008
add $zero,$zero,$zero; //0000000C
add $zero,$zero,$zero; //00000010
add $zero,$zero,$zero; //00000014
add $zero,$zero,$zero; //00000018
add $zero,$zero,$zero; //0000001C
//参数区
add $zero,$zero,$zero; //00000020 文本光标：0000XXYY
add $zero,$zero,$zero; //00000024 图形光标：00XXXYYY
add $zero,$zero,$zero; //00000028 键盘缓冲区头指针
add $zero,$zero,$zero; //0000002C 键盘缓冲区尾指针
add $zero,$zero,$zero; //00000030 键盘缓冲区低字： 最近4个ASCII码
add $zero,$zero,$zero; //00000034 键盘缓冲区第2字：次近4个ASCII码
add $zero,$zero,$zero; //00000038 键盘缓冲区第3字：次高4个ASCII码
add $zero,$zero,$zero; //0000003C 键盘缓冲区高字： 最高4个ASCII码
add $zero,$zero,$zero; //00000040 System Status Word:shif=D31,press_hold=d30,
add $zero,$zero,$zero; //00000044 键盘扫描码缓冲区低：去掉F0
add $zero,$zero,$zero; //00000048 键盘扫描码缓冲区高：去掉F0
add $zero,$zero,$zero; //0000004C
add $zero,$zero,$zero;
add $zero,$zero,$zero;
add $zero,$zero,$zero;
add $zero,$zero,$zero;
add $zero,$zero,$zero;
add $zero,$zero,$zero;
add $zero,$zero,$zero;
add $zero,$zero,$zero;
add $zero,$zero,$zero;
add $zero,$zero,$zero;
add $zero,$zero,$zero;
add $zero,$zero,$zero; //0000007C
//系统程序：00000080

//吃豆人：C；豆子：● 
//上下左右：移动；空格：重置，历史清零；回车：重新开始
//左上角：显示当前分数&历史最高分
start0:
   add $a1,$zero,$zero    // $a1=0 --> history highest

start:
   addi $sp,$zero,4000     //堆栈初始化，SP=4000
   addi $s3,$zero,0x2fff
   nor $s3,$s3,$zero       // $s3=0xffffd000 --> PS2
   addi $s7,$zero,0xc
   sll $s7,$s7,16          // $s7=0xc0000 --> VGA_TEXT(第一行)
   addi $s4,$s7,0x140      // $s4=0xc0140 --> VGA_TEXT(第二行)
   add $s6,$zero,$zero     // $s6=0 --> score
   addi $s5,$zero,0x0743   // PACMAN
   addi $s0,$zero,0x0707   // $s0=0x0707,'●',white bean  
   addi $s1,$zero,0x0207   // $s1=0x0207,'●',green bean
   addi $s2,$zero,0x0407   // $s2=0x0407,'●',red bomb

//随机生成 (×) 或 规定确定位置 (√)
//设置分数不同的豆子或炸弹：$s0白+1,$s1绿+5,$s2红bomb
show_beans:
   add $a3,$s4,$zero
   add $s4,$s7,$zero
   addi $s4,$s4,0x140
   addi $s4,$s4,4
   sw $s0,($s4)
   addi $s4,$s4,4
   sw $s0,($s4)
   addi $s4,$s4,4
   sw $s0,($s4)
   addi $s4,$s4,4
   sw $s0,($s4)
   addi $t0,$zero,0x10
   addi $t1,$zero,0x1  // $t1=1
white1:
   addi $s4,$s4,4
   sw $s0,($s4)
   sub $t0,$t0,$t1
   bne $t0,$zero,white1
   addi $s4,$s4,8
   sw $s1,($s4)
   addi $s4,$s4,4
   sw $s2,($s4)
   addi $s4,$s4,4
   sw $s0,($s4)
   addi $s4,$s4,4
   sw $s0,($s4)
   addi $s4,$s4,4
   sw $s0,($s4)
   addi $s4,$s4,4
   sw $s0,($s4)
   addi $s4,$s4,4
   sw $s1,($s4)
   addi $t0,$zero,0x10
white2:
   addi $s4,$s4,4
   sw $s0,($s4)
   addi $s4,$s4,4
   sw $s0,($s4)
   addi $s4,$s4,8
   sw $s0,($s4)
   addi $s4,$s4,4
   sw $s0,($s4)
   addi $s4,$s4,4
   sw $s1,($s4)
   addi $s4,$s4,4
   sw $s0,($s4)
   addi $s4,$s4,4
   sw $s0,($s4)
   addi $s4,$s4,4
   sw $s0,($s4)
   addi $s4,$s4,4
   sw $s0,($s4)
   addi $s4,$s4,4
   sw $s1,($s4)
   addi $s4,$s4,4
   sw $s1,($s4)
   addi $s4,$s4,8
   sw $s1,($s4)
   addi $s4,$s4,4
   sw $s2,($s4)
   sub $t0,$t0,$t1
   bne $t0,$zero,white2
   addi $s4,$s4,8
   sw $s1,($s4)
   addi $s4,$s4,4
   sw $s1,($s4)
   addi $s4,$s4,4
   sw $s2,($s4)
   addi $t0,$zero,0xF0
white3:
   addi $s4,$s4,4
   sw $s0,($s4)
   sub $t0,$t0,$t1
   bne $t0,$zero,white3
   addi $s4,$s4,4
   sw $s2,($s4)
   addi $s4,$s4,12
   sw $s0,($s4)
   addi $s4,$s4,4
   sw $s2,($s4)
   addi $s4,$s4,4
   sw $s0,($s4)
   addi $s4,$s4,4
   sw $s0,($s4)
   addi $s4,$s4,20
   addi $t0,$zero,0xC4
white4:
   addi $s4,$s4,8
   sw $s0,($s4)
   addi $s4,$s4,4
   sw $s0,($s4)
   addi $s4,$s4,4
   sw $s0,($s4)
   addi $s4,$s4,4
   sw $s1,($s4)
   addi $s4,$s4,4
   sw $s2,($s4)
   addi $s4,$s4,4
   sw $s0,($s4)
   addi $s4,$s4,4
   sw $s0,($s4)
   addi $s4,$s4,8
   sw $s0,($s4)
   addi $s4,$s4,4
   sw $s0,($s4)
   sub $t0,$t0,$t1
   bne $t0,$zero,white4
   addi $s4,$s4,4
   sw $s2,($s4)
   addi $t0,$zero,0xFF
white_green1:
   addi $s4,$s4,4
   sw $s1,($s4)
   addi $s4,$s4,4
   sw $s0,($s4)
   addi $s4,$s4,8
   sw $s0,($s4)
   sub $t0,$t0,$t1
   bne $t0,$zero,white_green1
   addi $s4,$s4,4
   sw $s2,($s4)
   addi $s4,$s4,4
   sw $s0,($s4)
   addi $s4,$s4,4
   sw $s1,($s4)
   addi $s4,$s4,4
   sw $s1,($s4)
   addi $s4,$s4,40
   addi $s4,$s4,4
   sw $s1,($s4)
   addi $s4,$s4,4
   sw $s0,($s4)
   addi $s4,$s4,4
   sw $s2,($s4)
   addi $s4,$s4,4
   sw $s2,($s4)
   addi $s4,$s4,4
   sw $s2,($s4)
   addi $s4,$s4,4
   sw $s1,($s4)
   addi $s4,$s4,4
   sw $s1,($s4)
   addi $t0,$zero,0xA0
white5:
   addi $s4,$s4,4
   sw $s0,($s4)
   sub $t0,$t0,$t1
   addi $s4,$s4,4
   sw $s1,($s4)
   addi $s4,$s4,8
   sw $s2,($s4)
   addi $s4,$s4,4
   sw $s1,($s4)
   addi $s4,$s4,4
   sw $s1,($s4)
   bne $t0,$zero,white5
   addi $s4,$s4,16
   sw $s1,($s4)
   addi $s4,$s4,4
   sw $s1,($s4)
   addi $s4,$s4,44
   addi $s4,$s4,4
   sw $s0,($s4)
   addi $s4,$s4,4
   sw $s2,($s4)
   addi $s4,$s4,4
   sw $s2,($s4)
   addi $s4,$s4,4
   sw $s0,($s4)
   addi $s4,$s4,4
   sw $s1,($s4)
   addi $s4,$s4,4
   sw $s2,($s4)
   addi $s4,$s4,16
   sw $s1,($s4)
   addi $s4,$s4,4
   sw $s1,($s4)
   addi $s4,$s4,4
   sw $s0,($s4)
   addi $s4,$s4,4
   sw $s2,($s4)
   addi $s4,$s4,4
   add $s4,$a3,$zero

show_title:
   add $a3,$s4,$zero
   add $s4,$s7,$zero
   addi $s4,$s4,4
   addi $a2,$zero,0x1750  //P
   sw $a2,($s4)
   addi $s4,$s4,4
   addi $a2,$zero,0x1741  //A
   sw $a2,($s4)
   addi $s4,$s4,4
   addi $a2,$zero,0x1743  //C
   sw $a2,($s4)
   addi $s4,$s4,4
   addi $a2,$zero,0x174D  //M
   sw $a2,($s4)
   addi $s4,$s4,4
   addi $a2,$zero,0x1741  //A
   sw $a2,($s4)
   addi $s4,$s4,4
   addi $a2,$zero,0x174E  //N
   sw $a2,($s4)
   addi $s4,$s4,8

   sw $s0,($s4)           //white bean
   addi $s4,$s4,4
   addi $a2,$zero,0x072B  //+
   sw $a2,($s4)
   addi $s4,$s4,4
   addi $a2,$zero,0x0731  //1
   sw $a2,($s4)
   addi $s4,$s4,4

   sw $s1,($s4)           //green bean
   addi $s4,$s4,4
   addi $a2,$zero,0x072B  //+
   sw $a2,($s4)
   addi $s4,$s4,4
   addi $a2,$zero,0x0735  //5
   sw $a2,($s4)
   addi $s4,$s4,4

   sw $s2,($s4)           //red bomb
   addi $s4,$s4,4
   addi $a2,$zero,0x0742  //B
   sw $a2,($s4)
   addi $s4,$s4,4
   addi $a2,$zero,0x074F  //O
   sw $a2,($s4)
   addi $s4,$s4,4
   addi $a2,$zero,0x074D  //M
   sw $a2,($s4)
   addi $s4,$s4,4
   addi $a2,$zero,0x0742  //B
   sw $a2,($s4)
   addi $s4,$s4,12

show_score:
   addi $a2,$zero,0x0653  //S
   sw $a2,($s4)
   addi $s4,$s4,4
   addi $a2,$zero,0x0663  //c
   sw $a2,($s4)
   addi $s4,$s4,4
   addi $a2,$zero,0x066F  //o
   sw $a2,($s4)
   addi $s4,$s4,4
   addi $a2,$zero,0x0672  //r
   sw $a2,($s4)
   addi $s4,$s4,4
   addi $a2,$zero,0x0665  //e
   sw $a2,($s4)
   addi $s4,$s4,8

   //total score in binary is less than 16-bit
   add $t3,$s6,$zero      //$t3=$s6 --> binary score
   add $a2,$zero,$zero    //$a2=0 --> decimal number
   addi $t2,$zero,10000   //$t2=10000,1000,100,10 --> devisor
wan:
   slt $t4,$t3,$t2
   bne $t4,$zero,wan_out
   sub $t3,$t3,$t2
   addi $a2,$a2,1
   j wan
wan_out:  
   addi $t2,$zero,1000
   sll $a2,$a2,4
qian:
   slt $t4,$t3,$t2
   bne $t4,$zero,qian_out
   sub $t3,$t3,$t2
   addi $a2,$a2,1
   j qian
qian_out:
   addi $t2,$zero,100
   sll $a2,$a2,4
bai:
   slt $t4,$t3,$t2
   bne $t4,$zero,bai_out
   sub $t3,$t3,$t2
   addi $a2,$a2,1
   j bai
bai_out:
   addi $t2,$zero,10
   sll $a2,$a2,4
shi:
   slt $t4,$t3,$t2
   bne $t4,$zero,shi_out
   sub $t3,$t3,$t2
   addi $a2,$a2,1
   j shi
shi_out:
   addi $t2,$zero,1
   sll $a2,$a2,4
ge:
   slt $t4,$t3,$t2
   bne $t4,$zero,print
   sub $t3,$t3,$t2
   addi $a2,$a2,1
   j ge
print:
    add $t8,$ra,$zero
    jal c1
    sw $v0,($s4)
    addi $s4,$s4,4
    jal c2
    sw $v0,($s4)
    addi $s4,$s4,4
    jal c3
    sw $v0,($s4)
    addi $s4,$s4,4
    jal c4
    sw $v0,($s4)
    addi $s4,$s4,4
    jal c5
    sw $v0,($s4)
    addi $s4,$s4,4
    add $ra,$t8,$zero

show_history:
   addi $s4,$s4,8
   addi $a2,$zero,0x0648  //H
   sw $a2,($s4)
   addi $s4,$s4,4
   addi $a2,$zero,0x0669  //i
   sw $a2,($s4)
   addi $s4,$s4,4
   addi $a2,$zero,0x0673  //s
   sw $a2,($s4)
   addi $s4,$s4,4
   addi $a2,$zero,0x0674  //t
   sw $a2,($s4)
   addi $s4,$s4,4
   addi $a2,$zero,0x066F  //o
   sw $a2,($s4)
   addi $s4,$s4,4
   addi $a2,$zero,0x0672  //r
   sw $a2,($s4)
   addi $s4,$s4,4
   addi $a2,$zero,0x0679  //y
   sw $a2,($s4)
   addi $s4,$s4,8

   addi $a2,$zero,0x0648  //H
   sw $a2,($s4)
   addi $s4,$s4,4
   addi $a2,$zero,0x0669  //i
   sw $a2,($s4)
   addi $s4,$s4,4
   addi $a2,$zero,0x0667  //g
   sw $a2,($s4)
   addi $s4,$s4,4
   addi $a2,$zero,0x0668  //h
   sw $a2,($s4)
   addi $s4,$s4,4
   addi $a2,$zero,0x0665  //e
   sw $a2,($s4)
   addi $s4,$s4,4
   addi $a2,$zero,0x0673  //s
   sw $a2,($s4)
   addi $s4,$s4,4
   addi $a2,$zero,0x0674  //t
   sw $a2,($s4)
   addi $s4,$s4,8

   //total score in binary is less than 16-bit
   add $t3,$a1,$zero      //$t3=$a1 --> binary score
   add $a2,$zero,$zero    //$a2=0 --> decimal number
   addi $t2,$zero,10000   //$t2=10000,1000,100,10 --> devisor
wan2:
   slt $t4,$t3,$t2
   bne $t4,$zero,wan_out2
   sub $t3,$t3,$t2
   addi $a2,$a2,1
   j wan2
wan_out2:  
   addi $t2,$zero,1000
   sll $a2,$a2,4
qian2:
   slt $t4,$t3,$t2
   bne $t4,$zero,qian_out2
   sub $t3,$t3,$t2
   addi $a2,$a2,1
   j qian2
qian_out2:
   addi $t2,$zero,100
   sll $a2,$a2,4
bai2:
   slt $t4,$t3,$t2
   bne $t4,$zero,bai_out2
   sub $t3,$t3,$t2
   addi $a2,$a2,1
   j bai2
bai_out2:
   addi $t2,$zero,10
   sll $a2,$a2,4
shi2:
   slt $t4,$t3,$t2
   bne $t4,$zero,shi_out2
   sub $t3,$t3,$t2
   addi $a2,$a2,1
   j shi2
shi_out2:
   addi $t2,$zero,1
   sll $a2,$a2,4
ge2:
   slt $t4,$t3,$t2
   bne $t4,$zero,print2
   sub $t3,$t3,$t2
   addi $a2,$a2,1
   j ge2
print2:
    add $t8,$ra,$zero
    jal c1
    sw $v0,($s4)
    addi $s4,$s4,4
    jal c2
    sw $v0,($s4)
    addi $s4,$s4,4
    jal c3
    sw $v0,($s4)
    addi $s4,$s4,4
    jal c4
    sw $v0,($s4)
    addi $s4,$s4,4
    jal c5
    sw $v0,($s4)
    addi $s4,$s4,4
    add $ra,$t8,$zero
    add $s4,$a3,$zero

read:
    sw $s5,($s4)		     //pacman
	lw $t0,($s3)             //$t0 = ps2
	slt $t1,$t0,$zero
	beq $t1,$zero,read
	andi $a0, $t0, 0x00FF    //mask ps2,only show $t0[8:0]
	jal decode
	beq $v0,$zero,read
	sw $v0,($s4)
	addi $s4,$s4,4
	sw $s5,($s4)		     //pacman
   j show_title

decode:
	addi $t0,$zero,0xE0
	beq $t0,$a0,E0          //for directions
	addi $t0,$zero,0x5A
	beq $t0,$a0,enter       //enter
   addi $t0,$zero,0x29
   beq $t0,$a0,space       //space
E0:
	lw $t1,($s3)
	add $v0,$zero,$zero
    lui $t0,0x8000
	addi $t0,$t0,0x75
	beq $t1,$t0,up       //go up
	lui $t0,0x8000
    addi $t0,$t0,0x6B
	beq $t1,$t0,left     //go left
	lui $t0,0x8000
    addi $t0,$t0,0x72
	beq $t1,$t0,down     //go down 
	lui $t0,0x8000
    addi $t0,$t0,0x74
	beq $t1,$t0,right    //go right
    lui $t0,0x8000
	addi $t0,$t0,0xF0
	beq $t1,$t0,break_E0
	jr $ra
up:
	sw $zero,($s4)
	addi $s4,$s4,0xFEC0     // -320
   lw $t0,($s4)
   sw $s5,($s4)
   beq $t0,$s0,eat_white
   beq $t0,$s1,eat_green
   beq $t0,$s2,eat_bomb
	jr $ra
left:
	sw $zero,($s4)
	addi $s4,$s4,0xFFFC     // -4
   lw $t0,($s4)
   beq $t0,$s0,eat_white
   beq $t0,$s1,eat_green
   beq $t0,$s2,eat_bomb
	sw $s5,($s4)
	jr $ra
down:
	sw $zero,($s4)
	addi $s4,$s4,0x140      // +320
   lw $t0,($s4)
   beq $t0,$s0,eat_white
   beq $t0,$s1,eat_green
   beq $t0,$s2,eat_bomb
	sw $s5,($s4)
	jr $ra
right:
	sw $zero,($s4)
	addi $s4,$s4,0x4       // +4
   lw $t0,($s4)
   beq $t0,$s0,eat_white
   beq $t0,$s1,eat_green
   beq $t0,$s2,eat_bomb
	sw $s5,($s4)
	jr $ra
enter:
   j start
   jr $ra
break_E0:
	lw $t1,($s3)
	jr $ra
space:
   j start0
   jr $ra

eat_white:
   addi $s6,$s6,0x1
   slt $t7,$s6,$a1
   beq $t7,$zero,update
   j show_title
eat_green:
   addi $s6,$s6,0x5
   slt $t7,$s6,$a1
   beq $t7,$zero,update
   j show_title
eat_bomb:
   j start
   
update:
   add $a1,$s6,$zero
   j show_title

c1:
    add $t5,$zero,$zero
    lui $t5,0x000F
    and $t5,$a2,$t5    //the fifth bit
    srl $t5,$t5,16
    andi $t5,$t5,0xf
    beq $t5,$zero,Out0
    addi $t6,$zero,1
    beq $t5,$t6,Out1
    addi $t6,$zero,2
    beq $t5,$t6,Out2
    addi $t6,$zero,3
    beq $t5,$t6,Out3
    addi $t6,$zero,4
    beq $t5,$t6,Out4
    addi $t6,$zero,5
    beq $t5,$t6,Out5
    addi $t6,$zero,6
    beq $t5,$t6,Out6
    addi $t6,$zero,7
    beq $t5,$t6,Out7
    addi $t6,$zero,8
    beq $t5,$t6,Out8
    addi $t6,$zero,9
    beq $t5,$t6,Out9
    jr $ra
c2:
    addi $t5,$zero,0xF000
    and $t5,$a2,$t5    //the fourth bit
    srl $t5,$t5,12
    andi $t5,$t5,0xf
    beq $t5,$zero,Out0
    addi $t6,$zero,1
    beq $t5,$t6,Out1
    addi $t6,$zero,2
    beq $t5,$t6,Out2
    addi $t6,$zero,3
    beq $t5,$t6,Out3
    addi $t6,$zero,4
    beq $t5,$t6,Out4
    addi $t6,$zero,5
    beq $t5,$t6,Out5
    addi $t6,$zero,6
    beq $t5,$t6,Out6
    addi $t6,$zero,7
    beq $t5,$t6,Out7
    addi $t6,$zero,8
    beq $t5,$t6,Out8
    addi $t6,$zero,9
    beq $t5,$t6,Out9
    jr $ra
c3:
    addi $t5,$zero,0xF00
    and $t5,$a2,$t5    //the third bit
    srl $t5,$t5,8
    andi $t5,$t5,0xf
    beq $t5,$zero,Out0
    addi $t6,$zero,1
    beq $t5,$t6,Out1
    addi $t6,$zero,2
    beq $t5,$t6,Out2
    addi $t6,$zero,3
    beq $t5,$t6,Out3
    addi $t6,$zero,4
    beq $t5,$t6,Out4
    addi $t6,$zero,5
    beq $t5,$t6,Out5
    addi $t6,$zero,6
    beq $t5,$t6,Out6
    addi $t6,$zero,7
    beq $t5,$t6,Out7
    addi $t6,$zero,8
    beq $t5,$t6,Out8
    addi $t6,$zero,9
    beq $t5,$t6,Out9
    jr $ra
c4:
    addi $t5,$zero,0xF0
    and $t5,$a2,$t5    //the second bit
    srl $t5,$t5,4
    andi $t5,$t5,0xf
    beq $t5,$zero,Out0
    addi $t6,$zero,1
    beq $t5,$t6,Out1
    addi $t6,$zero,2
    beq $t5,$t6,Out2
    addi $t6,$zero,3
    beq $t5,$t6,Out3
    addi $t6,$zero,4
    beq $t5,$t6,Out4
    addi $t6,$zero,5
    beq $t5,$t6,Out5
    addi $t6,$zero,6
    beq $t5,$t6,Out6
    addi $t6,$zero,7
    beq $t5,$t6,Out7
    addi $t6,$zero,8
    beq $t5,$t6,Out8
    addi $t6,$zero,9
    beq $t5,$t6,Out9
    jr $ra
c5:
    andi $t5,$a2,0xF   //the first bit
    andi $t5,$t5,0xf
    beq $t5,$zero,Out0
    addi $t6,$zero,1
    beq $t5,$t6,Out1
    addi $t6,$zero,2
    beq $t5,$t6,Out2
    addi $t6,$zero,3
    beq $t5,$t6,Out3
    addi $t6,$zero,4
    beq $t5,$t6,Out4
    addi $t6,$zero,5
    beq $t5,$t6,Out5
    addi $t6,$zero,6
    beq $t5,$t6,Out6
    addi $t6,$zero,7
    beq $t5,$t6,Out7
    addi $t6,$zero,8
    beq $t5,$t6,Out8
    addi $t6,$zero,9
    beq $t5,$t6,Out9
    jr $ra
Out0:
	addi $v0,$zero,0x730
	jr $ra
Out1:
	addi $v0,$zero,0x731
	jr $ra
Out2:
	addi $v0,$zero,0x732
	sw $v0,($s4)
	jr $ra
Out3:
	addi $v0,$zero,0x733
	jr $ra
Out4:
	addi $v0,$zero,0x734
	jr $ra
Out5:
	addi $v0,$zero,0x735
	jr $ra
Out6:
	addi $v0,$zero,0x736
	jr $ra
Out7:
	addi $v0,$zero,0x737
	jr $ra
Out8:
	addi $v0,$zero,0x738
	jr $ra
Out9:
	addi $v0,$zero,0x739
	jr $ra