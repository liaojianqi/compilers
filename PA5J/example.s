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
str_const17:
	.word	0
	.word	5
	.word	0
	.word	int_const0
	.byte	0	
	.align	2
	.word	-1
str_const16:
	.word	0
	.word	5
	.word	0
	.word	int_const1
	.ascii	"B"
	.byte	0	
	.align	2
	.word	-1
str_const15:
	.word	0
	.word	5
	.word	0
	.word	int_const1
	.ascii	"A"
	.byte	0	
	.align	2
	.word	-1
str_const14:
	.word	0
	.word	6
	.word	0
	.word	int_const2
	.ascii	"Main"
	.byte	0	
	.align	2
	.word	-1
str_const13:
	.word	0
	.word	6
	.word	0
	.word	int_const3
	.ascii	"String"
	.byte	0	
	.align	2
	.word	-1
str_const12:
	.word	0
	.word	6
	.word	0
	.word	int_const2
	.ascii	"Bool"
	.byte	0	
	.align	2
	.word	-1
str_const11:
	.word	0
	.word	5
	.word	0
	.word	int_const4
	.ascii	"Int"
	.byte	0	
	.align	2
	.word	-1
str_const10:
	.word	0
	.word	5
	.word	0
	.word	int_const5
	.ascii	"IO"
	.byte	0	
	.align	2
	.word	-1
str_const9:
	.word	0
	.word	6
	.word	0
	.word	int_const3
	.ascii	"Object"
	.byte	0	
	.align	2
	.word	-1
str_const8:
	.word	0
	.word	7
	.word	0
	.word	int_const6
	.ascii	"_prim_slot"
	.byte	0	
	.align	2
	.word	-1
str_const7:
	.word	0
	.word	7
	.word	0
	.word	int_const7
	.ascii	"SELF_TYPE"
	.byte	0	
	.align	2
	.word	-1
str_const6:
	.word	0
	.word	7
	.word	0
	.word	int_const7
	.ascii	"_no_class"
	.byte	0	
	.align	2
	.word	-1
str_const5:
	.word	0
	.word	8
	.word	0
	.word	int_const8
	.ascii	"<basic class>"
	.byte	0	
	.align	2
	.word	-1
str_const4:
	.word	0
	.word	7
	.word	0
	.word	int_const9
	.ascii	"not void"
	.byte	0	
	.align	2
	.word	-1
str_const3:
	.word	0
	.word	6
	.word	0
	.word	int_const2
	.ascii	"void"
	.byte	0	
	.align	2
	.word	-1
str_const2:
	.word	0
	.word	6
	.word	0
	.word	int_const10
	.ascii	"false"
	.byte	0	
	.align	2
	.word	-1
str_const1:
	.word	0
	.word	6
	.word	0
	.word	int_const2
	.ascii	"true"
	.byte	0	
	.align	2
	.word	-1
str_const0:
	.word	0
	.word	7
	.word	0
	.word	int_const6
	.ascii	"example.cl"
	.byte	0	
	.align	2
	.word	-1
int_const10:
	.word	1
	.word	4
	.word	0
	.word	5
	.word	-1
int_const9:
	.word	1
	.word	4
	.word	0
	.word	8
	.word	-1
int_const8:
	.word	1
	.word	4
	.word	0
	.word	13
	.word	-1
int_const7:
	.word	1
	.word	4
	.word	0
	.word	9
	.word	-1
int_const6:
	.word	1
	.word	4
	.word	0
	.word	10
	.word	-1
int_const5:
	.word	1
	.word	4
	.word	0
	.word	2
	.word	-1
int_const4:
	.word	1
	.word	4
	.word	0
	.word	3
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
	.word	4
	.word	-1
int_const1:
	.word	1
	.word	4
	.word	0
	.word	1
	.word	-1
int_const0:
	.word	1
	.word	4
	.word	0
	.word	0
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
	.word	0
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
	addiu	$sp $sp 12
	jr	$ra	
	.word	-1
IO_protObj:
	.word	4
	.word	3
	.word	0
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
	addiu	$sp $sp 12
	jr	$ra	
	.word	-1
Int_protObj:
	.word	1
	.word	4
	.word	0
	.word	0
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
	addiu	$sp $sp 12
	jr	$ra	
	.word	-1
String_protObj:
	.word	0
	.word	5
	.word	0
	.word	int_const0
	.byte	0	
	.align	2
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
	addiu	$sp $sp 12
	jr	$ra	
	.word	-1
A_protObj:
	.word	5
	.word	5
	.word	0
	.word	Int_protObj
	.word	Bool_protObj
A_init:
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
	addiu	$sp $sp 12
	jr	$ra	
	.word	-1
Main_protObj:
	.word	6
	.word	7
	.word	0
	.word	Int_protObj
	.word	String_protObj
	.word	Bool_protObj
	.word	IO_protObj
Main_init:
	addiu	$sp $sp -12
	sw	$fp 12($sp)
	sw	$s0 8($sp)
	sw	$ra 4($sp)
	addiu	$fp $sp 4
	move	$s0 $a0
	jal	IO_init
	move	$a0 $s0
	lw	$fp 12($sp)
	lw	$s0 8($sp)
	addiu	$sp $sp 12
	jr	$ra	
	.word	-1
B_protObj:
	.word	7
	.word	6
	.word	0
	.word	Int_protObj
	.word	Bool_protObj
	.word	String_protObj
B_init:
	addiu	$sp $sp -12
	sw	$fp 12($sp)
	sw	$s0 8($sp)
	sw	$ra 4($sp)
	addiu	$fp $sp 4
	move	$s0 $a0
	jal	A_init
	move	$a0 $s0
	lw	$fp 12($sp)
	lw	$s0 8($sp)
	addiu	$sp $sp 12
	jr	$ra	
class_nameTab:
	.word	str_const13
	.word	str_const11
	.word	str_const12
	.word	str_const9
	.word	str_const10
	.word	str_const15
	.word	str_const14
	.word	str_const16
	.globl	heap_start
heap_start:
	.word	0
	.text
	.globl	Main_init
	.globl	Int_init
	.globl	String_init
	.globl	Bool_init
	.globl	Main.main

# end of generated code
