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
str_const16:
	.word	0
	.word	5
	.word	String_dispTab
	.word	int_const2
	.byte	0	
	.align	2
	.word	-1
str_const15:
	.word	0
	.word	6
	.word	String_dispTab
	.word	int_const3
	.ascii	"Main"
	.byte	0	
	.align	2
	.word	-1
str_const14:
	.word	0
	.word	6
	.word	String_dispTab
	.word	int_const4
	.ascii	"Derived"
	.byte	0	
	.align	2
	.word	-1
str_const13:
	.word	0
	.word	6
	.word	String_dispTab
	.word	int_const3
	.ascii	"Base"
	.byte	0	
	.align	2
	.word	-1
str_const12:
	.word	0
	.word	6
	.word	String_dispTab
	.word	int_const5
	.ascii	"String"
	.byte	0	
	.align	2
	.word	-1
str_const11:
	.word	0
	.word	6
	.word	String_dispTab
	.word	int_const3
	.ascii	"Bool"
	.byte	0	
	.align	2
	.word	-1
str_const10:
	.word	0
	.word	5
	.word	String_dispTab
	.word	int_const6
	.ascii	"Int"
	.byte	0	
	.align	2
	.word	-1
str_const9:
	.word	0
	.word	5
	.word	String_dispTab
	.word	int_const1
	.ascii	"IO"
	.byte	0	
	.align	2
	.word	-1
str_const8:
	.word	0
	.word	6
	.word	String_dispTab
	.word	int_const5
	.ascii	"Object"
	.byte	0	
	.align	2
	.word	-1
str_const7:
	.word	0
	.word	7
	.word	String_dispTab
	.word	int_const7
	.ascii	"_prim_slot"
	.byte	0	
	.align	2
	.word	-1
str_const6:
	.word	0
	.word	7
	.word	String_dispTab
	.word	int_const8
	.ascii	"SELF_TYPE"
	.byte	0	
	.align	2
	.word	-1
str_const5:
	.word	0
	.word	7
	.word	String_dispTab
	.word	int_const8
	.ascii	"_no_class"
	.byte	0	
	.align	2
	.word	-1
str_const4:
	.word	0
	.word	8
	.word	String_dispTab
	.word	int_const9
	.ascii	"<basic class>"
	.byte	0	
	.align	2
	.word	-1
str_const3:
	.word	0
	.word	7
	.word	String_dispTab
	.word	int_const10
	.ascii	".  new: "
	.byte	0	
	.align	2
	.word	-1
str_const2:
	.word	0
	.word	6
	.word	String_dispTab
	.word	int_const11
	.ascii	"old: "
	.byte	0	
	.align	2
	.word	-1
str_const1:
	.word	0
	.word	5
	.word	String_dispTab
	.word	int_const0
	.ascii	"\n"
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
int_const11:
	.word	1
	.word	4
	.word	Int_dispTab
	.word	5
	.word	-1
int_const10:
	.word	1
	.word	4
	.word	Int_dispTab
	.word	8
	.word	-1
int_const9:
	.word	1
	.word	4
	.word	Int_dispTab
	.word	13
	.word	-1
int_const8:
	.word	1
	.word	4
	.word	Int_dispTab
	.word	9
	.word	-1
int_const7:
	.word	1
	.word	4
	.word	Int_dispTab
	.word	10
	.word	-1
int_const6:
	.word	1
	.word	4
	.word	Int_dispTab
	.word	3
	.word	-1
int_const5:
	.word	1
	.word	4
	.word	Int_dispTab
	.word	6
	.word	-1
int_const4:
	.word	1
	.word	4
	.word	Int_dispTab
	.word	7
	.word	-1
int_const3:
	.word	1
	.word	4
	.word	Int_dispTab
	.word	4
	.word	-1
int_const2:
	.word	1
	.word	4
	.word	Int_dispTab
	.word	0
	.word	-1
int_const1:
	.word	1
	.word	4
	.word	Int_dispTab
	.word	2
	.word	-1
int_const0:
	.word	1
	.word	4
	.word	Int_dispTab
	.word	1
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
	.word	int_const2
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
	.word	Object_protObj
Main_dispTab:
	.word	Object.abort
	.word	Object.type_name
	.word	Object.copy
	.word	Main.main
	.word	-1
Base_protObj:
	.word	6
	.word	4
	.word	Base_dispTab
	.word	int_const2
	.word	Base_init
	.word	IO_protObj
Base_dispTab:
	.word	Object.abort
	.word	Object.type_name
	.word	Object.copy
	.word	IO.out_string
	.word	IO.out_int
	.word	IO.in_string
	.word	IO.in_int
	.word	Base.report
	.word	Base.duplicate
	.word	-1
Derived_protObj:
	.word	7
	.word	5
	.word	Derived_dispTab
	.word	int_const2
	.word	int_const2
	.word	Derived_init
	.word	Base_protObj
Derived_dispTab:
	.word	Object.abort
	.word	Object.type_name
	.word	Object.copy
	.word	IO.out_string
	.word	IO.out_int
	.word	IO.in_string
	.word	IO.in_int
	.word	Derived.report
	.word	Base.duplicate
class_nameTab:
	.word	str_const12
	.word	str_const10
	.word	str_const11
	.word	str_const8
	.word	str_const9
	.word	str_const15
	.word	str_const13
	.word	str_const14
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
	la	$a0 Derived_protObj
	lw	$t1 8($a0)
	lw	$t1 8($t1)
	jalr	$t1
	lw	$t1 8($a0)
	lw	$t1 -8($t1)
	jalr	$t1
	lw	$fp 12($sp)
	lw	$s0 8($sp)
	lw	$ra 4($sp)
	addiu	$sp $sp 12
	jr	$ra	
Base_init:
	addiu	$sp $sp -12
	sw	$fp 12($sp)
	sw	$s0 8($sp)
	sw	$ra 4($sp)
	addiu	$fp $sp 4
	move	$s0 $a0
	la	$t1 int_const2
	sw	$t1 12($s0)
	move	$a0 $s0
	jal	IO_init
	addiu	$sp $sp -4
	la	$a0 int_const0
	sw	$a0 4($sp)
	move	$a0 $s0
	beq	$a0 $zero label0
	lw	$t1 8($a0)
	lw	$t1 28($t1)
	jalr	$t1
	b	label1
label0:
	li	$t1 1
	la	$a0 str_const0
	jal	_dispatch_abort
label1:
	la	$a0 int_const0
	sw	$a0 12($s0)
	move	$a0 $s0
	lw	$fp 12($sp)
	lw	$s0 8($sp)
	lw	$ra 4($sp)
	addiu	$sp $sp 12
	jr	$ra	
Base.report:
	addiu	$sp $sp -12
	sw	$fp 12($sp)
	sw	$s0 8($sp)
	sw	$ra 4($sp)
	addiu	$fp $sp 4
	move	$s0 $a0
	addiu	$sp $sp -4
	lw	$a0 12($fp)
	sw	$a0 4($sp)
	move	$a0 $s0
	beq	$a0 $zero label2
	lw	$t1 8($a0)
	lw	$t1 16($t1)
	jalr	$t1
	b	label3
label2:
	li	$t1 1
	la	$a0 str_const0
	jal	_dispatch_abort
label3:
	addiu	$sp $sp -4
	la	$a0 str_const1
	sw	$a0 4($sp)
	move	$a0 $s0
	beq	$a0 $zero label4
	lw	$t1 8($a0)
	lw	$t1 12($t1)
	jalr	$t1
	b	label5
label4:
	li	$t1 1
	la	$a0 str_const0
	jal	_dispatch_abort
label5:
	move	$a0 $s0
	lw	$fp 12($sp)
	lw	$s0 8($sp)
	lw	$ra 4($sp)
	addiu	$sp $sp 16
	jr	$ra	
Base.duplicate:
	addiu	$sp $sp -12
	sw	$fp 12($sp)
	sw	$s0 8($sp)
	sw	$ra 4($sp)
	addiu	$fp $sp 4
	move	$s0 $a0
	move	$a0 $s0
	lw	$t1 8($a0)
	lw	$t1 8($t1)
	jalr	$t1
	lw	$t1 8($a0)
	lw	$t1 -8($t1)
	jalr	$t1
	lw	$fp 12($sp)
	lw	$s0 8($sp)
	lw	$ra 4($sp)
	addiu	$sp $sp 12
	jr	$ra	
Derived_init:
	addiu	$sp $sp -12
	sw	$fp 12($sp)
	sw	$s0 8($sp)
	sw	$ra 4($sp)
	addiu	$fp $sp 4
	move	$s0 $a0
	la	$t1 int_const2
	sw	$t1 12($s0)
	la	$t1 int_const2
	sw	$t1 16($s0)
	move	$a0 $s0
	jal	Base_init
	addiu	$sp $sp -4
	la	$a0 int_const1
	sw	$a0 4($sp)
	move	$a0 $s0
	beq	$a0 $zero label6
	lw	$t1 8($a0)
	lw	$t1 28($t1)
	jalr	$t1
	b	label7
label6:
	li	$t1 1
	la	$a0 str_const0
	jal	_dispatch_abort
label7:
	la	$a0 int_const1
	sw	$a0 16($s0)
	move	$a0 $s0
	lw	$fp 12($sp)
	lw	$s0 8($sp)
	lw	$ra 4($sp)
	addiu	$sp $sp 12
	jr	$ra	
Derived.report:
	addiu	$sp $sp -12
	sw	$fp 12($sp)
	sw	$s0 8($sp)
	sw	$ra 4($sp)
	addiu	$fp $sp 4
	move	$s0 $a0
	addiu	$sp $sp -4
	la	$a0 str_const2
	sw	$a0 4($sp)
	move	$a0 $s0
	beq	$a0 $zero label8
	lw	$t1 8($a0)
	lw	$t1 12($t1)
	jalr	$t1
	b	label9
label8:
	li	$t1 1
	la	$a0 str_const0
	jal	_dispatch_abort
label9:
	addiu	$sp $sp -4
	lw	$a0 16($s0)
	sw	$a0 4($sp)
	move	$a0 $s0
	beq	$a0 $zero label10
	lw	$t1 8($a0)
	lw	$t1 16($t1)
	jalr	$t1
	b	label11
label10:
	li	$t1 1
	la	$a0 str_const0
	jal	_dispatch_abort
label11:
	addiu	$sp $sp -4
	la	$a0 str_const3
	sw	$a0 4($sp)
	move	$a0 $s0
	beq	$a0 $zero label12
	lw	$t1 8($a0)
	lw	$t1 12($t1)
	jalr	$t1
	b	label13
label12:
	li	$t1 1
	la	$a0 str_const0
	jal	_dispatch_abort
label13:
	lw	$a0 12($fp)
	sw	$a0 16($s0)
	addiu	$sp $sp -4
	lw	$a0 16($s0)
	sw	$a0 4($sp)
	move	$a0 $s0
	beq	$a0 $zero label14
	la	$t1 Base_dispTab
	lw	$t1 28($t1)
	jalr	$t1
	b	label15
label14:
	li	$t1 1
	la	$a0 str_const0
	jal	_dispatch_abort
label15:
	lw	$fp 12($sp)
	lw	$s0 8($sp)
	lw	$ra 4($sp)
	addiu	$sp $sp 16
	jr	$ra	

# end of generated code
