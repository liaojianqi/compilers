# start of generated code
	.data
	.align	2
	.globl	class_nameTab
	.globl	Main_protObj
	.globl	Int_protObj
	.globl	String_protObj
	.globl	bool_const0
	.globl	bool_const1
	.globl	_int_tag
	.globl	_bool_tag
	.globl	_string_tag
_int_tag:
	.word	1
_bool_tag:
	.word	2
_string_tag:
	.word	0
	.globl	_MemMgr_INITIALIZER
_MemMgr_INITIALIZER:
	.word	_NoGC_Init
	.globl	_MemMgr_COLLECTOR
_MemMgr_COLLECTOR:
	.word	_NoGC_Collect
	.globl	_MemMgr_TEST
_MemMgr_TEST:
	.word	0
	.word	-1
str_const12:
	.word	0
	.word	6
	.word	String_dispTab
	.word	int_const4
	.ascii	"Main"
	.byte	0	
	.align	2
	.word	-1
str_const11:
	.word	0
	.word	6
	.word	String_dispTab
	.word	int_const3
	.ascii	"String"
	.byte	0	
	.align	2
	.word	-1
str_const10:
	.word	0
	.word	6
	.word	String_dispTab
	.word	int_const4
	.ascii	"Bool"
	.byte	0	
	.align	2
	.word	-1
str_const9:
	.word	0
	.word	5
	.word	String_dispTab
	.word	int_const5
	.ascii	"Int"
	.byte	0	
	.align	2
	.word	-1
str_const8:
	.word	0
	.word	5
	.word	String_dispTab
	.word	int_const6
	.ascii	"IO"
	.byte	0	
	.align	2
	.word	-1
str_const7:
	.word	0
	.word	6
	.word	String_dispTab
	.word	int_const3
	.ascii	"Object"
	.byte	0	
	.align	2
	.word	-1
str_const6:
	.word	0
	.word	7
	.word	String_dispTab
	.word	int_const7
	.ascii	"_prim_slot"
	.byte	0	
	.align	2
	.word	-1
str_const5:
	.word	0
	.word	7
	.word	String_dispTab
	.word	int_const8
	.ascii	"SELF_TYPE"
	.byte	0	
	.align	2
	.word	-1
str_const4:
	.word	0
	.word	7
	.word	String_dispTab
	.word	int_const8
	.ascii	"_no_class"
	.byte	0	
	.align	2
	.word	-1
str_const3:
	.word	0
	.word	8
	.word	String_dispTab
	.word	int_const9
	.ascii	"<basic class>"
	.byte	0	
	.align	2
	.word	-1
str_const2:
	.word	0
	.word	5
	.word	String_dispTab
	.word	int_const1
	.byte	0	
	.align	2
	.word	-1
str_const1:
	.word	0
	.word	6
	.word	String_dispTab
	.word	int_const2
	.ascii	"hello"
	.byte	0	
	.align	2
	.word	-1
str_const0:
	.word	0
	.word	7
	.word	String_dispTab
	.word	int_const7
	.ascii	"example.cl"
	.byte	0	
	.align	2
	.word	-1
int_const9:
	.word	1
	.word	4
	.word	0
	.word	13
	.word	-1
int_const8:
	.word	1
	.word	4
	.word	0
	.word	9
	.word	-1
int_const7:
	.word	1
	.word	4
	.word	0
	.word	10
	.word	-1
int_const6:
	.word	1
	.word	4
	.word	0
	.word	2
	.word	-1
int_const5:
	.word	1
	.word	4
	.word	0
	.word	3
	.word	-1
int_const4:
	.word	1
	.word	4
	.word	0
	.word	4
	.word	-1
int_const3:
	.word	1
	.word	4
	.word	0
	.word	6
	.word	-1
int_const2:
	.word	1
	.word	4
	.word	0
	.word	5
	.word	-1
int_const1:
	.word	1
	.word	4
	.word	0
	.word	0
	.word	-1
int_const0:
	.word	1
	.word	4
	.word	0
	.word	1
	.word	-1
bool_const0:
	.word	2
	.word	4
	.word	0
	.word	0
	.word	-1
bool_const1:
	.word	2
	.word	4
	.word	0
	.word	1
	.word	-1
Object_protObj:
	.word	3
	.word	3
	.word	Object_dispTab
Object_dispTab:
	.word	Object.abort
	.word	Object.type_name
	.word	Object.copy
	.word	-1
IO_protObj:
	.word	4
	.word	3
	.word	IO_dispTab
IO_dispTab:
	.word	Object.abort
	.word	Object.type_name
	.word	Object.copy
	.word	IO.out_string
	.word	IO.out_int
	.word	IO.in_string
	.word	IO.in_int
	.word	-1
Int_protObj:
	.word	1
	.word	4
	.word	0
	.word	0
	.word	-1
String_protObj:
	.word	0
	.word	5
	.word	String_dispTab
	.word	int_const1
	.byte	0	
	.align	2
String_dispTab:
	.word	Object.abort
	.word	Object.type_name
	.word	Object.copy
	.word	String.length
	.word	String.concat
	.word	String.substr
	.word	-1
Main_protObj:
	.word	5
	.word	3
	.word	Main_dispTab
Main_dispTab:
	.word	Object.abort
	.word	Object.type_name
	.word	Object.copy
	.word	Main.main
class_nameTab:
	.word	str_const11
	.word	str_const9
	.word	str_const10
	.word	str_const7
	.word	str_const8
	.word	str_const12
	.globl	heap_start
heap_start:
	.word	0
	.text
	.globl	Main_init
	.globl	Int_init
	.globl	String_init
	.globl	Bool_init
	.globl	Main.main
Object_init:
	addiu	$sp $sp -12
	sw	$fp 12($sp)
	sw	$s0 8($sp)
	sw	$ra 4($sp)
	addiu	$fp $sp 4
	move	$s0 $a0
	move	$a0 $s0
	lw	$fp 12($sp)
	lw	$s0 8($sp)
	lw	$ra 4($sp)
	addiu	$sp $sp 12
	jr	$ra	
IO_init:
	addiu	$sp $sp -12
	sw	$fp 12($sp)
	sw	$s0 8($sp)
	sw	$ra 4($sp)
	addiu	$fp $sp 4
	move	$s0 $a0
	jal	Object_init
	move	$a0 $s0
	lw	$fp 12($sp)
	lw	$s0 8($sp)
	lw	$ra 4($sp)
	addiu	$sp $sp 12
	jr	$ra	
Int_init:
	addiu	$sp $sp -12
	sw	$fp 12($sp)
	sw	$s0 8($sp)
	sw	$ra 4($sp)
	addiu	$fp $sp 4
	move	$s0 $a0
	jal	Object_init
	move	$a0 $s0
	lw	$fp 12($sp)
	lw	$s0 8($sp)
	lw	$ra 4($sp)
	addiu	$sp $sp 12
	jr	$ra	
Bool_init:
	addiu	$sp $sp -12
	sw	$fp 12($sp)
	sw	$s0 8($sp)
	sw	$ra 4($sp)
	addiu	$fp $sp 4
	move	$s0 $a0
	jal	Object_init
	move	$a0 $s0
	lw	$fp 12($sp)
	lw	$s0 8($sp)
	lw	$ra 4($sp)
	addiu	$sp $sp 12
	jr	$ra	
String_init:
	addiu	$sp $sp -12
	sw	$fp 12($sp)
	sw	$s0 8($sp)
	sw	$ra 4($sp)
	addiu	$fp $sp 4
	move	$s0 $a0
	jal	Object_init
	move	$a0 $s0
	lw	$fp 12($sp)
	lw	$s0 8($sp)
	lw	$ra 4($sp)
	addiu	$sp $sp 12
	jr	$ra	
Main_init:
	addiu	$sp $sp -12
	sw	$fp 12($sp)
	sw	$s0 8($sp)
	sw	$ra 4($sp)
	addiu	$fp $sp 4
	move	$s0 $a0
	jal	Object_init
	move	$a0 $s0
	lw	$fp 12($sp)
	lw	$s0 8($sp)
	lw	$ra 4($sp)
	addiu	$sp $sp 12
	jr	$ra	
Main.main:
	addiu	$sp $sp -12
	sw	$fp 12($sp)
	sw	$s0 8($sp)
	sw	$ra 4($sp)
	addiu	$fp $sp 4
	move	$s0 $a0
	la	$a0 bool_const1
	sw	$a0 0($sp)
	addiu	$sp $sp -4
	la	$a0 bool_const0
	addiu	$sp $sp 4
	lw	$t1 0($sp)
	beq	$t1 $a0 label0
	beq	$t1 $zero label1
	beq	$a0 $zero label1
	lw	$t2 12($a0)
	lw	$t3 12($t1)
	beq	$t2 $t3 label0
	b	label1
label1:
	la	$a0 bool_const0
	b	label2
label0:
	la	$a0 bool_const1
label2:
	la	$t1 bool_const1
	beq	$a0 $t1 label3
	la	$a0 int_const1
	b	label4
label3:
	move	$a0 $s0
	lw	$t1 8($a0)
	lw	$t1 0($t1)
	jalr	$t1
label4:
	la	$a0 bool_const1
	sw	$a0 0($sp)
	addiu	$sp $sp -4
	la	$a0 bool_const1
	addiu	$sp $sp 4
	lw	$t1 0($sp)
	beq	$t1 $a0 label5
	beq	$t1 $zero label6
	beq	$a0 $zero label6
	lw	$t2 12($a0)
	lw	$t3 12($t1)
	beq	$t2 $t3 label5
	b	label6
label6:
	la	$a0 bool_const0
	b	label7
label5:
	la	$a0 bool_const1
label7:
	la	$t1 bool_const1
	beq	$a0 $t1 label8
	move	$a0 $s0
	lw	$t1 8($a0)
	lw	$t1 0($t1)
	jalr	$t1
	b	label9
label8:
	la	$a0 int_const1
label9:
	la	$a0 str_const1
	sw	$a0 0($sp)
	addiu	$sp $sp -4
	la	$a0 str_const1
	lw	$t1 8($a0)
	lw	$t1 8($t1)
	jalr	$t1
	addiu	$sp $sp 4
	lw	$t1 0($sp)
	beq	$t1 $a0 label10
	beq	$t1 $zero label11
	beq	$a0 $zero label11
	lw	$t2 12($a0)
	lw	$t3 12($t1)
	bne	$t2 $t3 label11
	li	$t2 4
	lw	$t3 12($a0)
	lw	$t3 12($t3)
	addiu	$t3 $t3 4
	div	$t3 $t3 $t2
	li	$t2 1
label12:
	sw	$t2 0($sp)
	addiu	$sp $sp -4
	sw	$t3 0($sp)
	addiu	$sp $sp -4
	addiu	$a0 $a0 4
	addiu	$t1 $t1 4
	lw	$t2 12($a0)
	lw	$t3 12($t1)
	bne	$t2 $t3 label11
	addiu	$sp $sp 4
	lw	$t3 0($sp)
	addiu	$sp $sp 4
	lw	$t2 0($sp)
	beq	$t2 $t3 label10
	addiu	$t2 $t2 1
	b	label12
label11:
	la	$a0 bool_const0
	b	label13
label10:
	la	$a0 bool_const1
label13:
	la	$t1 bool_const1
	beq	$a0 $t1 label14
	move	$a0 $s0
	lw	$t1 8($a0)
	lw	$t1 0($t1)
	jalr	$t1
	b	label15
label14:
	la	$a0 int_const1
label15:
	la	$a0 str_const2
	sw	$a0 0($sp)
	addiu	$sp $sp -4
	lw	$a0 -4($fp)
	sw	$a0 0($sp)
	addiu	$sp $sp -4
	la	$a0 str_const2
	addiu	$sp $sp 4
	lw	$t1 0($sp)
	beq	$t1 $a0 label16
	beq	$t1 $zero label17
	beq	$a0 $zero label17
	lw	$t2 12($a0)
	lw	$t3 12($t1)
	bne	$t2 $t3 label17
	li	$t2 4
	lw	$t3 12($a0)
	lw	$t3 12($t3)
	addiu	$t3 $t3 4
	div	$t3 $t3 $t2
	li	$t2 1
label18:
	sw	$t2 0($sp)
	addiu	$sp $sp -4
	sw	$t3 0($sp)
	addiu	$sp $sp -4
	addiu	$a0 $a0 4
	addiu	$t1 $t1 4
	lw	$t2 12($a0)
	lw	$t3 12($t1)
	bne	$t2 $t3 label17
	addiu	$sp $sp 4
	lw	$t3 0($sp)
	addiu	$sp $sp 4
	lw	$t2 0($sp)
	beq	$t2 $t3 label16
	addiu	$t2 $t2 1
	b	label18
label17:
	la	$a0 bool_const0
	b	label19
label16:
	la	$a0 bool_const1
label19:
	la	$t1 bool_const1
	beq	$a0 $t1 label20
	move	$a0 $s0
	lw	$t1 8($a0)
	lw	$t1 0($t1)
	jalr	$t1
	b	label21
label20:
	la	$a0 int_const1
label21:
	addiu	$sp $sp 4
	lw	$t1 0($sp)
	la	$a0 int_const2
	sw	$a0 0($sp)
	addiu	$sp $sp -4
	la	$a0 int_const3
	addiu	$sp $sp 4
	lw	$t1 0($sp)
	beq	$t1 $a0 label22
	beq	$t1 $zero label23
	beq	$a0 $zero label23
	lw	$t2 12($a0)
	lw	$t3 12($t1)
	beq	$t2 $t3 label22
	b	label23
label23:
	la	$a0 bool_const0
	b	label24
label22:
	la	$a0 bool_const1
label24:
	la	$t1 bool_const1
	beq	$a0 $t1 label25
	la	$a0 int_const1
	b	label26
label25:
	move	$a0 $s0
	lw	$t1 8($a0)
	lw	$t1 0($t1)
	jalr	$t1
label26:
	move	$a0 $s0
	lw	$fp 12($sp)
	lw	$s0 8($sp)
	lw	$ra 4($sp)
	addiu	$sp $sp 12
	jr	$ra	

# end of generated code
