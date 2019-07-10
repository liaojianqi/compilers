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
str_const13:
	.word	0
	.word	5
	.word	String_dispTab
	.word	int_const0
	.byte	0	
	.align	2
	.word	-1
str_const12:
	.word	0
	.word	6
	.word	String_dispTab
	.word	int_const1
	.ascii	"Main"
	.byte	0	
	.align	2
	.word	-1
str_const11:
	.word	0
	.word	6
	.word	String_dispTab
	.word	int_const2
	.ascii	"String"
	.byte	0	
	.align	2
	.word	-1
str_const10:
	.word	0
	.word	6
	.word	String_dispTab
	.word	int_const1
	.ascii	"Bool"
	.byte	0	
	.align	2
	.word	-1
str_const9:
	.word	0
	.word	5
	.word	String_dispTab
	.word	int_const3
	.ascii	"Int"
	.byte	0	
	.align	2
	.word	-1
str_const8:
	.word	0
	.word	5
	.word	String_dispTab
	.word	int_const4
	.ascii	"IO"
	.byte	0	
	.align	2
	.word	-1
str_const7:
	.word	0
	.word	6
	.word	String_dispTab
	.word	int_const2
	.ascii	"Object"
	.byte	0	
	.align	2
	.word	-1
str_const6:
	.word	0
	.word	7
	.word	String_dispTab
	.word	int_const5
	.ascii	"_prim_slot"
	.byte	0	
	.align	2
	.word	-1
str_const5:
	.word	0
	.word	7
	.word	String_dispTab
	.word	int_const6
	.ascii	"SELF_TYPE"
	.byte	0	
	.align	2
	.word	-1
str_const4:
	.word	0
	.word	7
	.word	String_dispTab
	.word	int_const6
	.ascii	"_no_class"
	.byte	0	
	.align	2
	.word	-1
str_const3:
	.word	0
	.word	8
	.word	String_dispTab
	.word	int_const7
	.ascii	"<basic class>"
	.byte	0	
	.align	2
	.word	-1
str_const2:
	.word	0
	.word	6
	.word	String_dispTab
	.word	int_const8
	.ascii	"main\n"
	.byte	0	
	.align	2
	.word	-1
str_const1:
	.word	0
	.word	6
	.word	String_dispTab
	.word	int_const9
	.ascii	"object\n"
	.byte	0	
	.align	2
	.word	-1
str_const0:
	.word	0
	.word	7
	.word	String_dispTab
	.word	int_const5
	.ascii	"example.cl"
	.byte	0	
	.align	2
	.word	-1
int_const9:
	.word	1
	.word	4
	.word	Int_dispTab
	.word	7
	.word	-1
int_const8:
	.word	1
	.word	4
	.word	Int_dispTab
	.word	5
	.word	-1
int_const7:
	.word	1
	.word	4
	.word	Int_dispTab
	.word	13
	.word	-1
int_const6:
	.word	1
	.word	4
	.word	Int_dispTab
	.word	9
	.word	-1
int_const5:
	.word	1
	.word	4
	.word	Int_dispTab
	.word	10
	.word	-1
int_const4:
	.word	1
	.word	4
	.word	Int_dispTab
	.word	2
	.word	-1
int_const3:
	.word	1
	.word	4
	.word	Int_dispTab
	.word	3
	.word	-1
int_const2:
	.word	1
	.word	4
	.word	Int_dispTab
	.word	6
	.word	-1
int_const1:
	.word	1
	.word	4
	.word	Int_dispTab
	.word	4
	.word	-1
int_const0:
	.word	1
	.word	4
	.word	Int_dispTab
	.word	0
	.word	-1
bool_const0:
	.word	2
	.word	4
	.word	Bool_dispTab
	.word	0
	.word	-1
bool_const1:
	.word	2
	.word	4
	.word	Bool_dispTab
	.word	1
	.word	-1
Object_protObj:
	.word	3
	.word	3
	.word	Object_dispTab
	.word	Object_init
	.word	0
Object_dispTab:
	.word	Object.abort
	.word	Object.type_name
	.word	Object.copy
	.word	-1
IO_protObj:
	.word	4
	.word	3
	.word	IO_dispTab
	.word	IO_init
	.word	Object_protObj
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
	.word	Int_dispTab
	.word	0
	.word	Int_init
	.word	Object_protObj
Int_dispTab:
	.word	Object.abort
	.word	Object.type_name
	.word	Object.copy
	.word	Bool_init
	.word	Object_protObj
Bool_dispTab:
	.word	Object.abort
	.word	Object.type_name
	.word	Object.copy
	.word	-1
String_protObj:
	.word	0
	.word	5
	.word	String_dispTab
	.word	int_const0
	.byte	0	
	.align	2
	.word	String_init
	.word	Object_protObj
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
	.word	Main_init
	.word	IO_protObj
Main_dispTab:
	.word	Object.abort
	.word	Object.type_name
	.word	Object.copy
	.word	IO.out_string
	.word	IO.out_int
	.word	IO.in_string
	.word	IO.in_int
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
	move	$a0 $s0
	jal	IO_init
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
	move	$a0 $s0
	sw	$a0 0($sp)
	addiu	$sp $sp -4
	lw	$a0 -4($fp)
	beq	$zero $a0 label1
	lw	$t1 0($a0)
	sw	$t1 0($sp)
	addiu	$sp $sp -4
	sw	$a0 0($sp)
	addiu	$sp $sp -4
	li	$t1 3
	sw	$t1 0($sp)
	addiu	$sp $sp -4
	la	$t1 label2
	sw	$t1 0($sp)
	addiu	$sp $sp -4
	li	$t1 5
	sw	$t1 0($sp)
	addiu	$sp $sp -4
	la	$t1 label3
	sw	$t1 0($sp)
	addiu	$sp $sp -4
	jal	label4
label2:
	addiu	$sp $sp -4
	la	$a0 str_const1
	sw	$a0 4($sp)
	move	$a0 $s0
	beq	$a0 $zero label5
	lw	$t1 8($a0)
	lw	$t1 12($t1)
	jalr	$t1
	b	label6
label5:
	li	$t1 1
	la	$a0 str_const0
	jal	_dispatch_abort
label6:
	jal	label0
label3:
	addiu	$sp $sp -4
	la	$a0 str_const2
	sw	$a0 4($sp)
	move	$a0 $s0
	beq	$a0 $zero label7
	lw	$t1 8($a0)
	lw	$t1 12($t1)
	jalr	$t1
	b	label8
label7:
	li	$t1 1
	la	$a0 str_const0
	jal	_dispatch_abort
label8:
	jal	label0
label4:
	li	$t3 4
	lw	$t1 8($sp)
	lw	$t2 -8($fp)
	beq	$t1 $t2 label9
	li	$t3 12
	lw	$t1 16($sp)
	lw	$t2 -8($fp)
	beq	$t1 $t2 label9
	li	$t1 3
	lw	$t2 -8($fp)
	beq	$t1 $t2 label10
	lw	$t1 -12($fp)
	lw	$t1 8($t1)
	lw	$t1 -4($t1)
	sw	$t1 -12($fp)
	lw	$t1 0($t1)
	sw	$t1 -8($fp)
	jal	label4
label10:
	move	$a0 $s0
	jal	_case_abort
label1:
	li	$t1 1
	la	$a0 str_const0
	jal	_case_abort2
label9:
	add	$t1 $t3 $sp
	lw	$t1 0($t1)
	jalr	$t1
label0:
	addiu	$sp $sp 24
	addiu	$sp $sp 4
	lw	$t1 0($sp)
	lw	$fp 12($sp)
	lw	$s0 8($sp)
	lw	$ra 4($sp)
	addiu	$sp $sp 12
	jr	$ra	

# end of generated code
