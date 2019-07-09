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
	.word	int_const6
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
	.word	5
	.word	String_dispTab
	.word	int_const0
	.ascii	"D"
	.byte	0	
	.align	2
	.word	-1
str_const13:
	.word	0
	.word	5
	.word	String_dispTab
	.word	int_const0
	.ascii	"C"
	.byte	0	
	.align	2
	.word	-1
str_const12:
	.word	0
	.word	5
	.word	String_dispTab
	.word	int_const0
	.ascii	"B"
	.byte	0	
	.align	2
	.word	-1
str_const11:
	.word	0
	.word	5
	.word	String_dispTab
	.word	int_const0
	.ascii	"A"
	.byte	0	
	.align	2
	.word	-1
str_const10:
	.word	0
	.word	6
	.word	String_dispTab
	.word	int_const5
	.ascii	"String"
	.byte	0	
	.align	2
	.word	-1
str_const9:
	.word	0
	.word	6
	.word	String_dispTab
	.word	int_const3
	.ascii	"Bool"
	.byte	0	
	.align	2
	.word	-1
str_const8:
	.word	0
	.word	5
	.word	String_dispTab
	.word	int_const2
	.ascii	"Int"
	.byte	0	
	.align	2
	.word	-1
str_const7:
	.word	0
	.word	5
	.word	String_dispTab
	.word	int_const1
	.ascii	"IO"
	.byte	0	
	.align	2
	.word	-1
str_const6:
	.word	0
	.word	6
	.word	String_dispTab
	.word	int_const5
	.ascii	"Object"
	.byte	0	
	.align	2
	.word	-1
str_const5:
	.word	0
	.word	7
	.word	String_dispTab
	.word	int_const7
	.ascii	"_prim_slot"
	.byte	0	
	.align	2
	.word	-1
str_const4:
	.word	0
	.word	7
	.word	String_dispTab
	.word	int_const8
	.ascii	"SELF_TYPE"
	.byte	0	
	.align	2
	.word	-1
str_const3:
	.word	0
	.word	7
	.word	String_dispTab
	.word	int_const8
	.ascii	"_no_class"
	.byte	0	
	.align	2
	.word	-1
str_const2:
	.word	0
	.word	8
	.word	String_dispTab
	.word	int_const9
	.ascii	"<basic class>"
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
	.word	0
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
	.word	5
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
	.word	3
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
	.word	int_const6
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
A_protObj:
	.word	5
	.word	3
	.word	A_dispTab
	.word	A_init
	.word	Object_protObj
A_dispTab:
	.word	Object.abort
	.word	Object.type_name
	.word	Object.copy
	.word	A.f
	.word	A.g
	.word	-1
Main_protObj:
	.word	6
	.word	7
	.word	Main_dispTab
	.word	0
	.word	0
	.word	0
	.word	0
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
	.word	-1
B_protObj:
	.word	7
	.word	3
	.word	B_dispTab
	.word	B_init
	.word	A_protObj
B_dispTab:
	.word	Object.abort
	.word	Object.type_name
	.word	Object.copy
	.word	A.f
	.word	B.g
	.word	-1
C_protObj:
	.word	8
	.word	3
	.word	C_dispTab
	.word	C_init
	.word	B_protObj
C_dispTab:
	.word	Object.abort
	.word	Object.type_name
	.word	Object.copy
	.word	C.f
	.word	B.g
	.word	-1
D_protObj:
	.word	9
	.word	3
	.word	D_dispTab
	.word	D_init
	.word	C_protObj
D_dispTab:
	.word	Object.abort
	.word	Object.type_name
	.word	Object.copy
	.word	D.f
	.word	D.g
class_nameTab:
	.word	str_const10
	.word	str_const8
	.word	str_const9
	.word	str_const6
	.word	str_const7
	.word	str_const11
	.word	str_const15
	.word	str_const12
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
	lw	$ra 4($sp)
	addiu	$sp $sp 12
	jr	$ra	
A.f:
	addiu	$sp $sp -12
	sw	$fp 12($sp)
	sw	$s0 8($sp)
	sw	$ra 4($sp)
	addiu	$fp $sp 4
	move	$s0 $a0
	la	$a0 int_const0
	lw	$fp 12($sp)
	lw	$s0 8($sp)
	lw	$ra 4($sp)
	addiu	$sp $sp 12
	jr	$ra	
A.g:
	addiu	$sp $sp -12
	sw	$fp 12($sp)
	sw	$s0 8($sp)
	sw	$ra 4($sp)
	addiu	$fp $sp 4
	move	$s0 $a0
	la	$a0 int_const1
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
	jal	IO_init
	move	$t1 $zero
	sw	$t1 12($s0)
	move	$t1 $zero
	sw	$t1 16($s0)
	move	$t1 $zero
	sw	$t1 20($s0)
	move	$t1 $zero
	sw	$t1 24($s0)
	la	$a0 A_protObj
	lw	$t1 8($a0)
	lw	$t1 8($t1)
	jalr	$t1
	lw	$t1 8($a0)
	lw	$t1 -8($t1)
	jalr	$t1
	sw	$a0 12($s0)
	la	$a0 B_protObj
	lw	$t1 8($a0)
	lw	$t1 8($t1)
	jalr	$t1
	lw	$t1 8($a0)
	lw	$t1 -8($t1)
	jalr	$t1
	sw	$a0 16($s0)
	la	$a0 C_protObj
	lw	$t1 8($a0)
	lw	$t1 8($t1)
	jalr	$t1
	lw	$t1 8($a0)
	lw	$t1 -8($t1)
	jalr	$t1
	sw	$a0 20($s0)
	la	$a0 D_protObj
	lw	$t1 8($a0)
	lw	$t1 8($t1)
	jalr	$t1
	lw	$t1 8($a0)
	lw	$t1 -8($t1)
	jalr	$t1
	sw	$a0 24($s0)
	move	$a0 $s0
	lw	$fp 12($sp)
	lw	$s0 8($sp)
	lw	$ra 4($sp)
	addiu	$sp $sp 12
	jr	$ra	
	la	$a0 A_protObj
	lw	$t1 8($a0)
	lw	$t1 8($t1)
	jalr	$t1
	lw	$t1 8($a0)
	lw	$t1 -8($t1)
	jalr	$t1
	la	$t1 Main_protObj
	sw	$a0 12($t1)
	la	$a0 B_protObj
	lw	$t1 8($a0)
	lw	$t1 8($t1)
	jalr	$t1
	lw	$t1 8($a0)
	lw	$t1 -8($t1)
	jalr	$t1
	la	$t1 Main_protObj
	sw	$a0 16($t1)
	la	$a0 C_protObj
	lw	$t1 8($a0)
	lw	$t1 8($t1)
	jalr	$t1
	lw	$t1 8($a0)
	lw	$t1 -8($t1)
	jalr	$t1
	la	$t1 Main_protObj
	sw	$a0 20($t1)
	la	$a0 D_protObj
	lw	$t1 8($a0)
	lw	$t1 8($t1)
	jalr	$t1
	lw	$t1 8($a0)
	lw	$t1 -8($t1)
	jalr	$t1
	la	$t1 Main_protObj
	sw	$a0 24($t1)
Main.main:
	addiu	$sp $sp -12
	sw	$fp 12($sp)
	sw	$s0 8($sp)
	sw	$ra 4($sp)
	addiu	$fp $sp 4
	move	$s0 $a0
	addiu	$sp $sp -4
	addiu	$sp $sp 0
	lw	$a0 12($s0)
	beq	$a0 $zero label0
	lw	$t1 8($a0)
	lw	$t1 12($t1)
	jalr	$t1
	b	label1
label0:
	li	$t1 1
	la	$a0 str_const0
	jal	_dispatch_abort
label1:
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
	addiu	$sp $sp 0
	lw	$a0 12($s0)
	beq	$a0 $zero label4
	lw	$t1 8($a0)
	lw	$t1 16($t1)
	jalr	$t1
	b	label5
label4:
	li	$t1 1
	la	$a0 str_const0
	jal	_dispatch_abort
label5:
	sw	$a0 4($sp)
	move	$a0 $s0
	beq	$a0 $zero label6
	lw	$t1 8($a0)
	lw	$t1 16($t1)
	jalr	$t1
	b	label7
label6:
	li	$t1 1
	la	$a0 str_const0
	jal	_dispatch_abort
label7:
	addiu	$sp $sp -4
	addiu	$sp $sp 0
	lw	$a0 16($s0)
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
	addiu	$sp $sp 0
	lw	$a0 16($s0)
	beq	$a0 $zero label12
	lw	$t1 8($a0)
	lw	$t1 16($t1)
	jalr	$t1
	b	label13
label12:
	li	$t1 1
	la	$a0 str_const0
	jal	_dispatch_abort
label13:
	sw	$a0 4($sp)
	move	$a0 $s0
	beq	$a0 $zero label14
	lw	$t1 8($a0)
	lw	$t1 16($t1)
	jalr	$t1
	b	label15
label14:
	li	$t1 1
	la	$a0 str_const0
	jal	_dispatch_abort
label15:
	addiu	$sp $sp -4
	addiu	$sp $sp 0
	lw	$a0 20($s0)
	beq	$a0 $zero label16
	lw	$t1 8($a0)
	lw	$t1 12($t1)
	jalr	$t1
	b	label17
label16:
	li	$t1 1
	la	$a0 str_const0
	jal	_dispatch_abort
label17:
	sw	$a0 4($sp)
	move	$a0 $s0
	beq	$a0 $zero label18
	lw	$t1 8($a0)
	lw	$t1 16($t1)
	jalr	$t1
	b	label19
label18:
	li	$t1 1
	la	$a0 str_const0
	jal	_dispatch_abort
label19:
	addiu	$sp $sp -4
	addiu	$sp $sp 0
	lw	$a0 20($s0)
	beq	$a0 $zero label20
	lw	$t1 8($a0)
	lw	$t1 16($t1)
	jalr	$t1
	b	label21
label20:
	li	$t1 1
	la	$a0 str_const0
	jal	_dispatch_abort
label21:
	sw	$a0 4($sp)
	move	$a0 $s0
	beq	$a0 $zero label22
	lw	$t1 8($a0)
	lw	$t1 16($t1)
	jalr	$t1
	b	label23
label22:
	li	$t1 1
	la	$a0 str_const0
	jal	_dispatch_abort
label23:
	addiu	$sp $sp -4
	addiu	$sp $sp 0
	lw	$a0 24($s0)
	beq	$a0 $zero label24
	lw	$t1 8($a0)
	lw	$t1 12($t1)
	jalr	$t1
	b	label25
label24:
	li	$t1 1
	la	$a0 str_const0
	jal	_dispatch_abort
label25:
	sw	$a0 4($sp)
	move	$a0 $s0
	beq	$a0 $zero label26
	lw	$t1 8($a0)
	lw	$t1 16($t1)
	jalr	$t1
	b	label27
label26:
	li	$t1 1
	la	$a0 str_const0
	jal	_dispatch_abort
label27:
	addiu	$sp $sp -4
	addiu	$sp $sp 0
	lw	$a0 24($s0)
	beq	$a0 $zero label28
	lw	$t1 8($a0)
	lw	$t1 16($t1)
	jalr	$t1
	b	label29
label28:
	li	$t1 1
	la	$a0 str_const0
	jal	_dispatch_abort
label29:
	sw	$a0 4($sp)
	move	$a0 $s0
	beq	$a0 $zero label30
	lw	$t1 8($a0)
	lw	$t1 16($t1)
	jalr	$t1
	b	label31
label30:
	li	$t1 1
	la	$a0 str_const0
	jal	_dispatch_abort
label31:
	addiu	$sp $sp -4
	addiu	$sp $sp 0
	lw	$a0 12($s0)
	beq	$a0 $zero label32
	la	$t1 A_dispTab
	lw	$t1 12($t1)
	jalr	$t1
	b	label33
label32:
	li	$t1 1
	la	$a0 str_const0
	jal	_dispatch_abort
label33:
	sw	$a0 4($sp)
	move	$a0 $s0
	beq	$a0 $zero label34
	lw	$t1 8($a0)
	lw	$t1 16($t1)
	jalr	$t1
	b	label35
label34:
	li	$t1 1
	la	$a0 str_const0
	jal	_dispatch_abort
label35:
	addiu	$sp $sp -4
	addiu	$sp $sp 0
	lw	$a0 12($s0)
	beq	$a0 $zero label36
	la	$t1 A_dispTab
	lw	$t1 16($t1)
	jalr	$t1
	b	label37
label36:
	li	$t1 1
	la	$a0 str_const0
	jal	_dispatch_abort
label37:
	sw	$a0 4($sp)
	move	$a0 $s0
	beq	$a0 $zero label38
	lw	$t1 8($a0)
	lw	$t1 16($t1)
	jalr	$t1
	b	label39
label38:
	li	$t1 1
	la	$a0 str_const0
	jal	_dispatch_abort
label39:
	addiu	$sp $sp -4
	addiu	$sp $sp 0
	lw	$a0 16($s0)
	beq	$a0 $zero label40
	la	$t1 A_dispTab
	lw	$t1 12($t1)
	jalr	$t1
	b	label41
label40:
	li	$t1 1
	la	$a0 str_const0
	jal	_dispatch_abort
label41:
	sw	$a0 4($sp)
	move	$a0 $s0
	beq	$a0 $zero label42
	lw	$t1 8($a0)
	lw	$t1 16($t1)
	jalr	$t1
	b	label43
label42:
	li	$t1 1
	la	$a0 str_const0
	jal	_dispatch_abort
label43:
	addiu	$sp $sp -4
	addiu	$sp $sp 0
	lw	$a0 16($s0)
	beq	$a0 $zero label44
	la	$t1 A_dispTab
	lw	$t1 16($t1)
	jalr	$t1
	b	label45
label44:
	li	$t1 1
	la	$a0 str_const0
	jal	_dispatch_abort
label45:
	sw	$a0 4($sp)
	move	$a0 $s0
	beq	$a0 $zero label46
	lw	$t1 8($a0)
	lw	$t1 16($t1)
	jalr	$t1
	b	label47
label46:
	li	$t1 1
	la	$a0 str_const0
	jal	_dispatch_abort
label47:
	addiu	$sp $sp -4
	addiu	$sp $sp 0
	lw	$a0 20($s0)
	beq	$a0 $zero label48
	la	$t1 A_dispTab
	lw	$t1 12($t1)
	jalr	$t1
	b	label49
label48:
	li	$t1 1
	la	$a0 str_const0
	jal	_dispatch_abort
label49:
	sw	$a0 4($sp)
	move	$a0 $s0
	beq	$a0 $zero label50
	lw	$t1 8($a0)
	lw	$t1 16($t1)
	jalr	$t1
	b	label51
label50:
	li	$t1 1
	la	$a0 str_const0
	jal	_dispatch_abort
label51:
	addiu	$sp $sp -4
	addiu	$sp $sp 0
	lw	$a0 20($s0)
	beq	$a0 $zero label52
	la	$t1 B_dispTab
	lw	$t1 16($t1)
	jalr	$t1
	b	label53
label52:
	li	$t1 1
	la	$a0 str_const0
	jal	_dispatch_abort
label53:
	sw	$a0 4($sp)
	move	$a0 $s0
	beq	$a0 $zero label54
	lw	$t1 8($a0)
	lw	$t1 16($t1)
	jalr	$t1
	b	label55
label54:
	li	$t1 1
	la	$a0 str_const0
	jal	_dispatch_abort
label55:
	addiu	$sp $sp -4
	addiu	$sp $sp 0
	lw	$a0 24($s0)
	beq	$a0 $zero label56
	la	$t1 C_dispTab
	lw	$t1 12($t1)
	jalr	$t1
	b	label57
label56:
	li	$t1 1
	la	$a0 str_const0
	jal	_dispatch_abort
label57:
	sw	$a0 4($sp)
	move	$a0 $s0
	beq	$a0 $zero label58
	lw	$t1 8($a0)
	lw	$t1 16($t1)
	jalr	$t1
	b	label59
label58:
	li	$t1 1
	la	$a0 str_const0
	jal	_dispatch_abort
label59:
	addiu	$sp $sp -4
	addiu	$sp $sp 0
	lw	$a0 24($s0)
	beq	$a0 $zero label60
	la	$t1 B_dispTab
	lw	$t1 16($t1)
	jalr	$t1
	b	label61
label60:
	li	$t1 1
	la	$a0 str_const0
	jal	_dispatch_abort
label61:
	sw	$a0 4($sp)
	move	$a0 $s0
	beq	$a0 $zero label62
	lw	$t1 8($a0)
	lw	$t1 16($t1)
	jalr	$t1
	b	label63
label62:
	li	$t1 1
	la	$a0 str_const0
	jal	_dispatch_abort
label63:
	la	$a0 B_protObj
	lw	$t1 8($a0)
	lw	$t1 8($t1)
	jalr	$t1
	lw	$t1 8($a0)
	lw	$t1 -8($t1)
	jalr	$t1
	sw	$a0 12($s0)
	la	$a0 C_protObj
	lw	$t1 8($a0)
	lw	$t1 8($t1)
	jalr	$t1
	lw	$t1 8($a0)
	lw	$t1 -8($t1)
	jalr	$t1
	sw	$a0 16($s0)
	la	$a0 D_protObj
	lw	$t1 8($a0)
	lw	$t1 8($t1)
	jalr	$t1
	lw	$t1 8($a0)
	lw	$t1 -8($t1)
	jalr	$t1
	sw	$a0 20($s0)
	addiu	$sp $sp -4
	addiu	$sp $sp 0
	lw	$a0 12($s0)
	beq	$a0 $zero label64
	lw	$t1 8($a0)
	lw	$t1 12($t1)
	jalr	$t1
	b	label65
label64:
	li	$t1 1
	la	$a0 str_const0
	jal	_dispatch_abort
label65:
	sw	$a0 4($sp)
	move	$a0 $s0
	beq	$a0 $zero label66
	lw	$t1 8($a0)
	lw	$t1 16($t1)
	jalr	$t1
	b	label67
label66:
	li	$t1 1
	la	$a0 str_const0
	jal	_dispatch_abort
label67:
	addiu	$sp $sp -4
	addiu	$sp $sp 0
	lw	$a0 12($s0)
	beq	$a0 $zero label68
	lw	$t1 8($a0)
	lw	$t1 16($t1)
	jalr	$t1
	b	label69
label68:
	li	$t1 1
	la	$a0 str_const0
	jal	_dispatch_abort
label69:
	sw	$a0 4($sp)
	move	$a0 $s0
	beq	$a0 $zero label70
	lw	$t1 8($a0)
	lw	$t1 16($t1)
	jalr	$t1
	b	label71
label70:
	li	$t1 1
	la	$a0 str_const0
	jal	_dispatch_abort
label71:
	addiu	$sp $sp -4
	addiu	$sp $sp 0
	lw	$a0 16($s0)
	beq	$a0 $zero label72
	lw	$t1 8($a0)
	lw	$t1 12($t1)
	jalr	$t1
	b	label73
label72:
	li	$t1 1
	la	$a0 str_const0
	jal	_dispatch_abort
label73:
	sw	$a0 4($sp)
	move	$a0 $s0
	beq	$a0 $zero label74
	lw	$t1 8($a0)
	lw	$t1 16($t1)
	jalr	$t1
	b	label75
label74:
	li	$t1 1
	la	$a0 str_const0
	jal	_dispatch_abort
label75:
	addiu	$sp $sp -4
	addiu	$sp $sp 0
	lw	$a0 16($s0)
	beq	$a0 $zero label76
	lw	$t1 8($a0)
	lw	$t1 16($t1)
	jalr	$t1
	b	label77
label76:
	li	$t1 1
	la	$a0 str_const0
	jal	_dispatch_abort
label77:
	sw	$a0 4($sp)
	move	$a0 $s0
	beq	$a0 $zero label78
	lw	$t1 8($a0)
	lw	$t1 16($t1)
	jalr	$t1
	b	label79
label78:
	li	$t1 1
	la	$a0 str_const0
	jal	_dispatch_abort
label79:
	addiu	$sp $sp -4
	addiu	$sp $sp 0
	lw	$a0 20($s0)
	beq	$a0 $zero label80
	lw	$t1 8($a0)
	lw	$t1 12($t1)
	jalr	$t1
	b	label81
label80:
	li	$t1 1
	la	$a0 str_const0
	jal	_dispatch_abort
label81:
	sw	$a0 4($sp)
	move	$a0 $s0
	beq	$a0 $zero label82
	lw	$t1 8($a0)
	lw	$t1 16($t1)
	jalr	$t1
	b	label83
label82:
	li	$t1 1
	la	$a0 str_const0
	jal	_dispatch_abort
label83:
	addiu	$sp $sp -4
	addiu	$sp $sp 0
	lw	$a0 20($s0)
	beq	$a0 $zero label84
	lw	$t1 8($a0)
	lw	$t1 16($t1)
	jalr	$t1
	b	label85
label84:
	li	$t1 1
	la	$a0 str_const0
	jal	_dispatch_abort
label85:
	sw	$a0 4($sp)
	move	$a0 $s0
	beq	$a0 $zero label86
	lw	$t1 8($a0)
	lw	$t1 16($t1)
	jalr	$t1
	b	label87
label86:
	li	$t1 1
	la	$a0 str_const0
	jal	_dispatch_abort
label87:
	addiu	$sp $sp -4
	la	$a0 str_const1
	sw	$a0 4($sp)
	move	$a0 $s0
	beq	$a0 $zero label88
	lw	$t1 8($a0)
	lw	$t1 12($t1)
	jalr	$t1
	b	label89
label88:
	li	$t1 1
	la	$a0 str_const0
	jal	_dispatch_abort
label89:
	lw	$fp 12($sp)
	lw	$s0 8($sp)
	lw	$ra 4($sp)
	addiu	$sp $sp 12
	jr	$ra	
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
	lw	$ra 4($sp)
	addiu	$sp $sp 12
	jr	$ra	
B.g:
	addiu	$sp $sp -12
	sw	$fp 12($sp)
	sw	$s0 8($sp)
	sw	$ra 4($sp)
	addiu	$fp $sp 4
	move	$s0 $a0
	la	$a0 int_const2
	lw	$fp 12($sp)
	lw	$s0 8($sp)
	lw	$ra 4($sp)
	addiu	$sp $sp 12
	jr	$ra	
C_init:
	addiu	$sp $sp -12
	sw	$fp 12($sp)
	sw	$s0 8($sp)
	sw	$ra 4($sp)
	addiu	$fp $sp 4
	move	$s0 $a0
	jal	B_init
	move	$a0 $s0
	lw	$fp 12($sp)
	lw	$s0 8($sp)
	lw	$ra 4($sp)
	addiu	$sp $sp 12
	jr	$ra	
C.f:
	addiu	$sp $sp -12
	sw	$fp 12($sp)
	sw	$s0 8($sp)
	sw	$ra 4($sp)
	addiu	$fp $sp 4
	move	$s0 $a0
	la	$a0 int_const3
	lw	$fp 12($sp)
	lw	$s0 8($sp)
	lw	$ra 4($sp)
	addiu	$sp $sp 12
	jr	$ra	
D_init:
	addiu	$sp $sp -12
	sw	$fp 12($sp)
	sw	$s0 8($sp)
	sw	$ra 4($sp)
	addiu	$fp $sp 4
	move	$s0 $a0
	jal	C_init
	move	$a0 $s0
	lw	$fp 12($sp)
	lw	$s0 8($sp)
	lw	$ra 4($sp)
	addiu	$sp $sp 12
	jr	$ra	
D.f:
	addiu	$sp $sp -12
	sw	$fp 12($sp)
	sw	$s0 8($sp)
	sw	$ra 4($sp)
	addiu	$fp $sp 4
	move	$s0 $a0
	la	$a0 int_const4
	lw	$fp 12($sp)
	lw	$s0 8($sp)
	lw	$ra 4($sp)
	addiu	$sp $sp 12
	jr	$ra	
D.g:
	addiu	$sp $sp -12
	sw	$fp 12($sp)
	sw	$s0 8($sp)
	sw	$ra 4($sp)
	addiu	$fp $sp 4
	move	$s0 $a0
	la	$a0 int_const5
	lw	$fp 12($sp)
	lw	$s0 8($sp)
	lw	$ra 4($sp)
	addiu	$sp $sp 12
	jr	$ra	

# end of generated code
