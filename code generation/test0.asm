.data
newline: .asciiz "\n"
msg_index_out_of_bound_exception: .asciiz "Index out of bound exception\n"
msg_null_pointer_exception: .asciiz "Null pointer exception\n"

.text

# main
move $fp, $sp
sw $ra, 0($fp)
addi $sp, $sp, -4
# print
# Call: NewObject, new QS.Start()
sw $fp, 0($sp)
addi $sp, $sp, -4
li $v0, 10  # load 10
sw $v0, 0($sp)
addi $sp, $sp, -4
jal QS
beq $v0, $0, _null_pointer_exception  # object call
sw $v0, 0($sp)
addi $sp, $sp, -4
jal QS_Start
addi $sp, $sp, 8
addi $sp, $sp, 4
lw $fp, 0($sp)
move $a0, $v0
jal _print_int
# exit
li $v0, 10
syscall

QS:
sw $ra, 0($sp)
addi $sp, $sp, -4
li $a0, 2
jal _alloc_new_object
addi $sp, $sp, 4
lw $ra, 0($sp)
jr $ra
QS_Start:
move $fp, $sp
addi $sp, $sp, -8
sw $ra, 0($fp)
# Assign aux01
# Call: This, QS.Init()
sw $fp, 0($sp)
addi $sp, $sp, -4
# load parametersz
lw $v0, 8($fp)
sw $v0, 0($sp)
addi $sp, $sp, -4
lw $v0, 4($fp)
beq $v0, $0, _null_pointer_exception  # object call
sw $v0, 0($sp)
addi $sp, $sp, -4
jal QS_Init
addi $sp, $sp, 8
addi $sp, $sp, 4
lw $fp, 0($sp)
sw $v0, -4($fp)
# Assign aux01
# Call: This, QS.Print()
sw $fp, 0($sp)
addi $sp, $sp, -4
lw $v0, 4($fp)
beq $v0, $0, _null_pointer_exception  # object call
sw $v0, 0($sp)
addi $sp, $sp, -4
jal QS_Print
addi $sp, $sp, 4
addi $sp, $sp, 4
lw $fp, 0($sp)
sw $v0, -4($fp)
# print
li $v0, 9999  # load 9999
move $a0, $v0
jal _print_int
# Assign aux01
# Minus
sw $t0, 0($sp)
addi $sp, $sp, -4
# load fieldsize
lw $v0, 4($fp)
lw $v0, 8($v0)
move $t0, $v0
li $v0, 1  # load 1
sub $v0, $t0, $v0
addi $sp, $sp, 4
lw $t0, 0($sp)
sw $v0, -4($fp)
# Assign aux01
# Call: This, QS.Sort()
sw $fp, 0($sp)
addi $sp, $sp, -4
# load local variable aux01
lw $v0, -4($fp)
sw $v0, 0($sp)
addi $sp, $sp, -4
li $v0, 0  # load 0
sw $v0, 0($sp)
addi $sp, $sp, -4
lw $v0, 4($fp)
beq $v0, $0, _null_pointer_exception  # object call
sw $v0, 0($sp)
addi $sp, $sp, -4
jal QS_Sort
addi $sp, $sp, 12
addi $sp, $sp, 4
lw $fp, 0($sp)
sw $v0, -4($fp)
# Assign aux01
# Call: This, QS.Print()
sw $fp, 0($sp)
addi $sp, $sp, -4
lw $v0, 4($fp)
beq $v0, $0, _null_pointer_exception  # object call
sw $v0, 0($sp)
addi $sp, $sp, -4
jal QS_Print
addi $sp, $sp, 4
addi $sp, $sp, 4
lw $fp, 0($sp)
sw $v0, -4($fp)
li $v0, 0  # load 0
lw $ra, 0($fp)
addi $sp, $sp, 8
jr $ra
QS_Sort:
move $fp, $sp
addi $sp, $sp, -36
sw $ra, 0($fp)
# Assign t
li $v0, 0  # load 0
sw $v0, -20($fp)
If1:
# LessThan
sw $t0, 0($sp)
addi $sp, $sp, -4
# load parameterleft
lw $v0, 8($fp)
move $t0, $v0
# load parameterright
lw $v0, 12($fp)
slt $v0, $t0, $v0
addi $sp, $sp, 4
lw $t0, 0($sp)
beq $v0, $0, If1_false
j If1_true
If1_true:
# Assign v
sw $t0, 0($sp)
addi $sp, $sp, -4
sw $t1, 0($sp)
addi $sp, $sp, -4
# load fieldnumber
lw $v0, 4($fp)
lw $v0, 4($v0)
beq $v0, $0, _null_pointer_exception  # array lookup
move $t0, $v0
# load parameterright
lw $v0, 12($fp)
lw $t1, 0($t0)
bge $v0, $t1, _array_index_out_of_bound_exception
li $t1, 4
addi $v0, $v0, 1
mult $v0, $t1
mflo $v0
add $t0, $t0, $v0
lw $v0, 0($t0)
addi $sp, $sp, 4
lw $t1, 0($sp)
addi $sp, $sp, 4
lw $t0, 0($sp)
sw $v0, -4($fp)
# Assign i
# Minus
sw $t0, 0($sp)
addi $sp, $sp, -4
# load parameterleft
lw $v0, 8($fp)
move $t0, $v0
li $v0, 1  # load 1
sub $v0, $t0, $v0
addi $sp, $sp, 4
lw $t0, 0($sp)
sw $v0, -8($fp)
# Assign j
# load parameterright
lw $v0, 12($fp)
sw $v0, -12($fp)
# Assign cont01
li $v0, 1  # load true
sw $v0, -24($fp)
While1:
# load local variable cont01
lw $v0, -24($fp)
beq $v0, $0, While1_end
j While1_loop
While1_loop:
# Assign cont02
li $v0, 1  # load true
sw $v0, -28($fp)
While2:
# load local variable cont02
lw $v0, -28($fp)
beq $v0, $0, While2_end
j While2_loop
While2_loop:
# Assign i
# Plus
sw $t0, 0($sp)
addi $sp, $sp, -4
# load local variable i
lw $v0, -8($fp)
move $t0, $v0
li $v0, 1  # load 1
add $v0, $t0, $v0
addi $sp, $sp, 4
lw $t0, 0($sp)
sw $v0, -8($fp)
# Assign aux03
sw $t0, 0($sp)
addi $sp, $sp, -4
sw $t1, 0($sp)
addi $sp, $sp, -4
# load fieldnumber
lw $v0, 4($fp)
lw $v0, 4($v0)
beq $v0, $0, _null_pointer_exception  # array lookup
move $t0, $v0
# load local variable i
lw $v0, -8($fp)
lw $t1, 0($t0)
bge $v0, $t1, _array_index_out_of_bound_exception
li $t1, 4
addi $v0, $v0, 1
mult $v0, $t1
mflo $v0
add $t0, $t0, $v0
lw $v0, 0($t0)
addi $sp, $sp, 4
lw $t1, 0($sp)
addi $sp, $sp, 4
lw $t0, 0($sp)
sw $v0, -32($fp)
If2:
# Not
# LessThan
sw $t0, 0($sp)
addi $sp, $sp, -4
# load local variable aux03
lw $v0, -32($fp)
move $t0, $v0
# load local variable v
lw $v0, -4($fp)
slt $v0, $t0, $v0
addi $sp, $sp, 4
lw $t0, 0($sp)
seq $v0, $v0, $0
beq $v0, $0, If2_false
j If2_true
If2_true:
# Assign cont02
li $v0, 0  # load false
sw $v0, -28($fp)
j If2_end
If2_false:
# Assign cont02
li $v0, 1  # load true
sw $v0, -28($fp)
j If2_end
If2_end:
j While2
While2_end:
# Assign cont02
li $v0, 1  # load true
sw $v0, -28($fp)
While3:
# load local variable cont02
lw $v0, -28($fp)
beq $v0, $0, While3_end
j While3_loop
While3_loop:
# Assign j
# Minus
sw $t0, 0($sp)
addi $sp, $sp, -4
# load local variable j
lw $v0, -12($fp)
move $t0, $v0
li $v0, 1  # load 1
sub $v0, $t0, $v0
addi $sp, $sp, 4
lw $t0, 0($sp)
sw $v0, -12($fp)
# Assign aux03
sw $t0, 0($sp)
addi $sp, $sp, -4
sw $t1, 0($sp)
addi $sp, $sp, -4
# load fieldnumber
lw $v0, 4($fp)
lw $v0, 4($v0)
beq $v0, $0, _null_pointer_exception  # array lookup
move $t0, $v0
# load local variable j
lw $v0, -12($fp)
lw $t1, 0($t0)
bge $v0, $t1, _array_index_out_of_bound_exception
li $t1, 4
addi $v0, $v0, 1
mult $v0, $t1
mflo $v0
add $t0, $t0, $v0
lw $v0, 0($t0)
addi $sp, $sp, 4
lw $t1, 0($sp)
addi $sp, $sp, 4
lw $t0, 0($sp)
sw $v0, -32($fp)
If3:
# Not
# LessThan
sw $t0, 0($sp)
addi $sp, $sp, -4
# load local variable v
lw $v0, -4($fp)
move $t0, $v0
# load local variable aux03
lw $v0, -32($fp)
slt $v0, $t0, $v0
addi $sp, $sp, 4
lw $t0, 0($sp)
seq $v0, $v0, $0
beq $v0, $0, If3_false
j If3_true
If3_true:
# Assign cont02
li $v0, 0  # load false
sw $v0, -28($fp)
j If3_end
If3_false:
# Assign cont02
li $v0, 1  # load true
sw $v0, -28($fp)
j If3_end
If3_end:
j While3
While3_end:
# Assign t
sw $t0, 0($sp)
addi $sp, $sp, -4
sw $t1, 0($sp)
addi $sp, $sp, -4
# load fieldnumber
lw $v0, 4($fp)
lw $v0, 4($v0)
beq $v0, $0, _null_pointer_exception  # array lookup
move $t0, $v0
# load local variable i
lw $v0, -8($fp)
lw $t1, 0($t0)
bge $v0, $t1, _array_index_out_of_bound_exception
li $t1, 4
addi $v0, $v0, 1
mult $v0, $t1
mflo $v0
add $t0, $t0, $v0
lw $v0, 0($t0)
addi $sp, $sp, 4
lw $t1, 0($sp)
addi $sp, $sp, 4
lw $t0, 0($sp)
sw $v0, -20($fp)
# ArrayAssign number
sw $t0, 0($sp)
addi $sp, $sp, -4
sw $t1, 0($sp)
addi $sp, $sp, -4
lw $t0, 4($fp)  # load this
lw $t0, 4($t0)
beq $t0, $0, _null_pointer_exception
# load local variable i
lw $v0, -8($fp)
lw $t1, 0($t0)
bge $v0, $t1, _array_index_out_of_bound_exception
li $t1, 4
addi $v0, $v0, 1
mult $v0, $t1
mflo $v0
add $t0, $t0, $v0
sw $t0, 0($sp)
addi $sp, $sp, -4
sw $t1, 0($sp)
addi $sp, $sp, -4
# load fieldnumber
lw $v0, 4($fp)
lw $v0, 4($v0)
beq $v0, $0, _null_pointer_exception  # array lookup
move $t0, $v0
# load local variable j
lw $v0, -12($fp)
lw $t1, 0($t0)
bge $v0, $t1, _array_index_out_of_bound_exception
li $t1, 4
addi $v0, $v0, 1
mult $v0, $t1
mflo $v0
add $t0, $t0, $v0
lw $v0, 0($t0)
addi $sp, $sp, 4
lw $t1, 0($sp)
addi $sp, $sp, 4
lw $t0, 0($sp)
sw, $v0, 0($t0)
addi $sp, $sp, 4
lw $t1, 0($sp)
addi $sp, $sp, 4
lw $t0, 0($sp)
# ArrayAssign number
sw $t0, 0($sp)
addi $sp, $sp, -4
sw $t1, 0($sp)
addi $sp, $sp, -4
lw $t0, 4($fp)  # load this
lw $t0, 4($t0)
beq $t0, $0, _null_pointer_exception
# load local variable j
lw $v0, -12($fp)
lw $t1, 0($t0)
bge $v0, $t1, _array_index_out_of_bound_exception
li $t1, 4
addi $v0, $v0, 1
mult $v0, $t1
mflo $v0
add $t0, $t0, $v0
# load local variable t
lw $v0, -20($fp)
sw, $v0, 0($t0)
addi $sp, $sp, 4
lw $t1, 0($sp)
addi $sp, $sp, 4
lw $t0, 0($sp)
If4:
# LessThan
sw $t0, 0($sp)
addi $sp, $sp, -4
# load local variable j
lw $v0, -12($fp)
move $t0, $v0
# Plus
sw $t0, 0($sp)
addi $sp, $sp, -4
# load local variable i
lw $v0, -8($fp)
move $t0, $v0
li $v0, 1  # load 1
add $v0, $t0, $v0
addi $sp, $sp, 4
lw $t0, 0($sp)
slt $v0, $t0, $v0
addi $sp, $sp, 4
lw $t0, 0($sp)
beq $v0, $0, If4_false
j If4_true
If4_true:
# Assign cont01
li $v0, 0  # load false
sw $v0, -24($fp)
j If4_end
If4_false:
# Assign cont01
li $v0, 1  # load true
sw $v0, -24($fp)
j If4_end
If4_end:
j While1
While1_end:
# ArrayAssign number
sw $t0, 0($sp)
addi $sp, $sp, -4
sw $t1, 0($sp)
addi $sp, $sp, -4
lw $t0, 4($fp)  # load this
lw $t0, 4($t0)
beq $t0, $0, _null_pointer_exception
# load local variable j
lw $v0, -12($fp)
lw $t1, 0($t0)
bge $v0, $t1, _array_index_out_of_bound_exception
li $t1, 4
addi $v0, $v0, 1
mult $v0, $t1
mflo $v0
add $t0, $t0, $v0
sw $t0, 0($sp)
addi $sp, $sp, -4
sw $t1, 0($sp)
addi $sp, $sp, -4
# load fieldnumber
lw $v0, 4($fp)
lw $v0, 4($v0)
beq $v0, $0, _null_pointer_exception  # array lookup
move $t0, $v0
# load local variable i
lw $v0, -8($fp)
lw $t1, 0($t0)
bge $v0, $t1, _array_index_out_of_bound_exception
li $t1, 4
addi $v0, $v0, 1
mult $v0, $t1
mflo $v0
add $t0, $t0, $v0
lw $v0, 0($t0)
addi $sp, $sp, 4
lw $t1, 0($sp)
addi $sp, $sp, 4
lw $t0, 0($sp)
sw, $v0, 0($t0)
addi $sp, $sp, 4
lw $t1, 0($sp)
addi $sp, $sp, 4
lw $t0, 0($sp)
# ArrayAssign number
sw $t0, 0($sp)
addi $sp, $sp, -4
sw $t1, 0($sp)
addi $sp, $sp, -4
lw $t0, 4($fp)  # load this
lw $t0, 4($t0)
beq $t0, $0, _null_pointer_exception
# load local variable i
lw $v0, -8($fp)
lw $t1, 0($t0)
bge $v0, $t1, _array_index_out_of_bound_exception
li $t1, 4
addi $v0, $v0, 1
mult $v0, $t1
mflo $v0
add $t0, $t0, $v0
sw $t0, 0($sp)
addi $sp, $sp, -4
sw $t1, 0($sp)
addi $sp, $sp, -4
# load fieldnumber
lw $v0, 4($fp)
lw $v0, 4($v0)
beq $v0, $0, _null_pointer_exception  # array lookup
move $t0, $v0
# load parameterright
lw $v0, 12($fp)
lw $t1, 0($t0)
bge $v0, $t1, _array_index_out_of_bound_exception
li $t1, 4
addi $v0, $v0, 1
mult $v0, $t1
mflo $v0
add $t0, $t0, $v0
lw $v0, 0($t0)
addi $sp, $sp, 4
lw $t1, 0($sp)
addi $sp, $sp, 4
lw $t0, 0($sp)
sw, $v0, 0($t0)
addi $sp, $sp, 4
lw $t1, 0($sp)
addi $sp, $sp, 4
lw $t0, 0($sp)
# ArrayAssign number
sw $t0, 0($sp)
addi $sp, $sp, -4
sw $t1, 0($sp)
addi $sp, $sp, -4
lw $t0, 4($fp)  # load this
lw $t0, 4($t0)
beq $t0, $0, _null_pointer_exception
# load parameterright
lw $v0, 12($fp)
lw $t1, 0($t0)
bge $v0, $t1, _array_index_out_of_bound_exception
li $t1, 4
addi $v0, $v0, 1
mult $v0, $t1
mflo $v0
add $t0, $t0, $v0
# load local variable t
lw $v0, -20($fp)
sw, $v0, 0($t0)
addi $sp, $sp, 4
lw $t1, 0($sp)
addi $sp, $sp, 4
lw $t0, 0($sp)
# Assign nt
# Call: This, QS.Sort()
sw $fp, 0($sp)
addi $sp, $sp, -4
# Minus
sw $t0, 0($sp)
addi $sp, $sp, -4
# load local variable i
lw $v0, -8($fp)
move $t0, $v0
li $v0, 1  # load 1
sub $v0, $t0, $v0
addi $sp, $sp, 4
lw $t0, 0($sp)
sw $v0, 0($sp)
addi $sp, $sp, -4
# load parameterleft
lw $v0, 8($fp)
sw $v0, 0($sp)
addi $sp, $sp, -4
lw $v0, 4($fp)
beq $v0, $0, _null_pointer_exception  # object call
sw $v0, 0($sp)
addi $sp, $sp, -4
jal QS_Sort
addi $sp, $sp, 12
addi $sp, $sp, 4
lw $fp, 0($sp)
sw $v0, -16($fp)
# Assign nt
# Call: This, QS.Sort()
sw $fp, 0($sp)
addi $sp, $sp, -4
# load parameterright
lw $v0, 12($fp)
sw $v0, 0($sp)
addi $sp, $sp, -4
# Plus
sw $t0, 0($sp)
addi $sp, $sp, -4
# load local variable i
lw $v0, -8($fp)
move $t0, $v0
li $v0, 1  # load 1
add $v0, $t0, $v0
addi $sp, $sp, 4
lw $t0, 0($sp)
sw $v0, 0($sp)
addi $sp, $sp, -4
lw $v0, 4($fp)
beq $v0, $0, _null_pointer_exception  # object call
sw $v0, 0($sp)
addi $sp, $sp, -4
jal QS_Sort
addi $sp, $sp, 12
addi $sp, $sp, 4
lw $fp, 0($sp)
sw $v0, -16($fp)
j If1_end
If1_false:
# Assign nt
li $v0, 0  # load 0
sw $v0, -16($fp)
j If1_end
If1_end:
li $v0, 0  # load 0
lw $ra, 0($fp)
addi $sp, $sp, 36
jr $ra
QS_Print:
move $fp, $sp
addi $sp, $sp, -8
sw $ra, 0($fp)
# Assign j
li $v0, 0  # load 0
sw $v0, -4($fp)
While4:
# LessThan
sw $t0, 0($sp)
addi $sp, $sp, -4
# load local variable j
lw $v0, -4($fp)
move $t0, $v0
# load fieldsize
lw $v0, 4($fp)
lw $v0, 8($v0)
slt $v0, $t0, $v0
addi $sp, $sp, 4
lw $t0, 0($sp)
beq $v0, $0, While4_end
j While4_loop
While4_loop:
# print
sw $t0, 0($sp)
addi $sp, $sp, -4
sw $t1, 0($sp)
addi $sp, $sp, -4
# load fieldnumber
lw $v0, 4($fp)
lw $v0, 4($v0)
beq $v0, $0, _null_pointer_exception  # array lookup
move $t0, $v0
# load local variable j
lw $v0, -4($fp)
lw $t1, 0($t0)
bge $v0, $t1, _array_index_out_of_bound_exception
li $t1, 4
addi $v0, $v0, 1
mult $v0, $t1
mflo $v0
add $t0, $t0, $v0
lw $v0, 0($t0)
addi $sp, $sp, 4
lw $t1, 0($sp)
addi $sp, $sp, 4
lw $t0, 0($sp)
move $a0, $v0
jal _print_int
# Assign j
# Plus
sw $t0, 0($sp)
addi $sp, $sp, -4
# load local variable j
lw $v0, -4($fp)
move $t0, $v0
li $v0, 1  # load 1
add $v0, $t0, $v0
addi $sp, $sp, 4
lw $t0, 0($sp)
sw $v0, -4($fp)
j While4
While4_end:
li $v0, 0  # load 0
lw $ra, 0($fp)
addi $sp, $sp, 8
jr $ra
QS_Init:
move $fp, $sp
addi $sp, $sp, -4
sw $ra, 0($fp)
# Assign size
sw $t0, 0($sp)
addi $sp, $sp, -4
lw $t0, 4($fp)  # load this
# load parametersz
lw $v0, 8($fp)
sw $v0, 8($t0)
addi $sp, $sp, 4
lw $t0, 0($sp)
# Assign number
sw $t0, 0($sp)
addi $sp, $sp, -4
lw $t0, 4($fp)  # load this
# NewArray
# load parametersz
lw $v0, 8($fp)
move $a0, $v0
jal _alloc_int_array
sw $v0, 4($t0)
addi $sp, $sp, 4
lw $t0, 0($sp)
# ArrayAssign number
sw $t0, 0($sp)
addi $sp, $sp, -4
sw $t1, 0($sp)
addi $sp, $sp, -4
lw $t0, 4($fp)  # load this
lw $t0, 4($t0)
beq $t0, $0, _null_pointer_exception
li $v0, 0  # load 0
lw $t1, 0($t0)
bge $v0, $t1, _array_index_out_of_bound_exception
li $t1, 4
addi $v0, $v0, 1
mult $v0, $t1
mflo $v0
add $t0, $t0, $v0
li $v0, 20  # load 20
sw, $v0, 0($t0)
addi $sp, $sp, 4
lw $t1, 0($sp)
addi $sp, $sp, 4
lw $t0, 0($sp)
# ArrayAssign number
sw $t0, 0($sp)
addi $sp, $sp, -4
sw $t1, 0($sp)
addi $sp, $sp, -4
lw $t0, 4($fp)  # load this
lw $t0, 4($t0)
beq $t0, $0, _null_pointer_exception
li $v0, 1  # load 1
lw $t1, 0($t0)
bge $v0, $t1, _array_index_out_of_bound_exception
li $t1, 4
addi $v0, $v0, 1
mult $v0, $t1
mflo $v0
add $t0, $t0, $v0
li $v0, 7  # load 7
sw, $v0, 0($t0)
addi $sp, $sp, 4
lw $t1, 0($sp)
addi $sp, $sp, 4
lw $t0, 0($sp)
# ArrayAssign number
sw $t0, 0($sp)
addi $sp, $sp, -4
sw $t1, 0($sp)
addi $sp, $sp, -4
lw $t0, 4($fp)  # load this
lw $t0, 4($t0)
beq $t0, $0, _null_pointer_exception
li $v0, 2  # load 2
lw $t1, 0($t0)
bge $v0, $t1, _array_index_out_of_bound_exception
li $t1, 4
addi $v0, $v0, 1
mult $v0, $t1
mflo $v0
add $t0, $t0, $v0
li $v0, 12  # load 12
sw, $v0, 0($t0)
addi $sp, $sp, 4
lw $t1, 0($sp)
addi $sp, $sp, 4
lw $t0, 0($sp)
# ArrayAssign number
sw $t0, 0($sp)
addi $sp, $sp, -4
sw $t1, 0($sp)
addi $sp, $sp, -4
lw $t0, 4($fp)  # load this
lw $t0, 4($t0)
beq $t0, $0, _null_pointer_exception
li $v0, 3  # load 3
lw $t1, 0($t0)
bge $v0, $t1, _array_index_out_of_bound_exception
li $t1, 4
addi $v0, $v0, 1
mult $v0, $t1
mflo $v0
add $t0, $t0, $v0
li $v0, 18  # load 18
sw, $v0, 0($t0)
addi $sp, $sp, 4
lw $t1, 0($sp)
addi $sp, $sp, 4
lw $t0, 0($sp)
# ArrayAssign number
sw $t0, 0($sp)
addi $sp, $sp, -4
sw $t1, 0($sp)
addi $sp, $sp, -4
lw $t0, 4($fp)  # load this
lw $t0, 4($t0)
beq $t0, $0, _null_pointer_exception
li $v0, 4  # load 4
lw $t1, 0($t0)
bge $v0, $t1, _array_index_out_of_bound_exception
li $t1, 4
addi $v0, $v0, 1
mult $v0, $t1
mflo $v0
add $t0, $t0, $v0
li $v0, 2  # load 2
sw, $v0, 0($t0)
addi $sp, $sp, 4
lw $t1, 0($sp)
addi $sp, $sp, 4
lw $t0, 0($sp)
# ArrayAssign number
sw $t0, 0($sp)
addi $sp, $sp, -4
sw $t1, 0($sp)
addi $sp, $sp, -4
lw $t0, 4($fp)  # load this
lw $t0, 4($t0)
beq $t0, $0, _null_pointer_exception
li $v0, 5  # load 5
lw $t1, 0($t0)
bge $v0, $t1, _array_index_out_of_bound_exception
li $t1, 4
addi $v0, $v0, 1
mult $v0, $t1
mflo $v0
add $t0, $t0, $v0
li $v0, 11  # load 11
sw, $v0, 0($t0)
addi $sp, $sp, 4
lw $t1, 0($sp)
addi $sp, $sp, 4
lw $t0, 0($sp)
# ArrayAssign number
sw $t0, 0($sp)
addi $sp, $sp, -4
sw $t1, 0($sp)
addi $sp, $sp, -4
lw $t0, 4($fp)  # load this
lw $t0, 4($t0)
beq $t0, $0, _null_pointer_exception
li $v0, 6  # load 6
lw $t1, 0($t0)
bge $v0, $t1, _array_index_out_of_bound_exception
li $t1, 4
addi $v0, $v0, 1
mult $v0, $t1
mflo $v0
add $t0, $t0, $v0
li $v0, 6  # load 6
sw, $v0, 0($t0)
addi $sp, $sp, 4
lw $t1, 0($sp)
addi $sp, $sp, 4
lw $t0, 0($sp)
# ArrayAssign number
sw $t0, 0($sp)
addi $sp, $sp, -4
sw $t1, 0($sp)
addi $sp, $sp, -4
lw $t0, 4($fp)  # load this
lw $t0, 4($t0)
beq $t0, $0, _null_pointer_exception
li $v0, 7  # load 7
lw $t1, 0($t0)
bge $v0, $t1, _array_index_out_of_bound_exception
li $t1, 4
addi $v0, $v0, 1
mult $v0, $t1
mflo $v0
add $t0, $t0, $v0
li $v0, 9  # load 9
sw, $v0, 0($t0)
addi $sp, $sp, 4
lw $t1, 0($sp)
addi $sp, $sp, 4
lw $t0, 0($sp)
# ArrayAssign number
sw $t0, 0($sp)
addi $sp, $sp, -4
sw $t1, 0($sp)
addi $sp, $sp, -4
lw $t0, 4($fp)  # load this
lw $t0, 4($t0)
beq $t0, $0, _null_pointer_exception
li $v0, 8  # load 8
lw $t1, 0($t0)
bge $v0, $t1, _array_index_out_of_bound_exception
li $t1, 4
addi $v0, $v0, 1
mult $v0, $t1
mflo $v0
add $t0, $t0, $v0
li $v0, 19  # load 19
sw, $v0, 0($t0)
addi $sp, $sp, 4
lw $t1, 0($sp)
addi $sp, $sp, 4
lw $t0, 0($sp)
# ArrayAssign number
sw $t0, 0($sp)
addi $sp, $sp, -4
sw $t1, 0($sp)
addi $sp, $sp, -4
lw $t0, 4($fp)  # load this
lw $t0, 4($t0)
beq $t0, $0, _null_pointer_exception
li $v0, 9  # load 9
lw $t1, 0($t0)
bge $v0, $t1, _array_index_out_of_bound_exception
li $t1, 4
addi $v0, $v0, 1
mult $v0, $t1
mflo $v0
add $t0, $t0, $v0
li $v0, 5  # load 5
sw, $v0, 0($t0)
addi $sp, $sp, 4
lw $t1, 0($sp)
addi $sp, $sp, 4
lw $t0, 0($sp)
li $v0, 0  # load 0
lw $ra, 0($fp)
addi $sp, $sp, 4
jr $ra
_print_int: # System.out.println(int)
li $v0, 1
syscall
la $a0, newline
li $a1, 1
li $v0, 4   # print newline
syscall
jr $ra

_null_pointer_exception:
la $a0, msg_null_pointer_exception
li $a1, 23
li $v0, 4
syscall
li $v0, 10
syscall

_array_index_out_of_bound_exception:
la $a0, msg_index_out_of_bound_exception
li $a1, 29
li $v0, 4
syscall
li $v0, 10
syscall

_alloc_int_array: # new int [$a0]
addi $a2, $a0, 0  # Save length in $a2
addi $a0, $a0, 1  # One more word to store the length
sll $a0, $a0, 2   # multiple by 4 bytes
li $v0, 9         # allocate space
syscall

sw $a2, 0($v0)    # Store array length
addi $t1, $v0, 4  # begin address = ($v0 + 4); address of the first element
add $t2, $v0, $a0 # loop until ($v0 + 4*(length+1)), the address after the last element

_alloc_int_array_loop:
beq $t1, $t2, _alloc_int_array_loop_end
sw $0, 0($t1)
addi $t1, $t1, 4
j _alloc_int_array_loop
_alloc_int_array_loop_end:

jr $ra

_alloc_new_object: # new object
addi $a2, $a0, 0  # Save length in $a2
addi $a0, $a0, 1  # One more word to store the length
sll $a0, $a0, 2   # multiple by 4 bytes
li $v0, 9         # allocate space
syscall

sw $a2, 0($v0)    # Store array length
addi $t1, $v0, 4  # begin address = ($v0 + 4); address of the first element
add $t2, $v0, $a0 # loop until ($v0 + 4*(length+1)), the address after the last element

_alloc_new_object_loop:
beq $t1, $t2, _alloc_new_object_loop_end
sw $0, 0($t1)
addi $t1, $t1, 4
j _alloc_new_object_loop
_alloc_new_object_loop_end:

jr $ra

