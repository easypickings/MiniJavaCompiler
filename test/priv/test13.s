    .text
    .globl  main
main:
    sw    $ra -4($sp)
    sw    $fp -8($sp)
    move  $fp $sp
    addi  $sp $sp -8
    li    $t4 8
    move  $a0 $t4
    jal   _halloc
    move  $t5 $v0
    move  $t3 $t5
    li    $t2 0
    li    $t1 0
L1: slt   $t0 $t2 $t4
    bne   $t0 1   L2
    sw    $t1 ($t3)
    addi  $t3 $t3 4
    addi  $t2 $t2 4
    b     L1
L2: move  $a0 $t5
    jal   Test_start_1
    move  $t0 $v0
    move  $a0 $t0
    jal   _print
    lw    $ra -4($fp)
    lw    $fp -8($fp)
    addi  $sp $sp 8
    jr    $ra

    .text
    .globl  Test_start_1
Test_start_1:
    sw    $ra -4($sp)
    sw    $fp -8($sp)
    move  $fp $sp
    addi  $sp $sp -8
    move  $t3 $a0
    li    $t7 10
    addi  $sp $sp -4
    sw    $v0 ($sp)
    li    $v0 4
    mul   $t6 $t7 $v0
    lw    $v0 ($sp)
    addi  $sp $sp 4
    addi  $t6 $t6 4
    move  $a0 $t6
    jal   _halloc
    move  $t5 $v0
    move  $t4 $t5
    li    $t2 0
    li    $t1 0
L3: slt   $t0 $t2 $t6
    bne   $t0 1   L4
    sw    $t1 ($t4)
    addi  $t4 $t4 4
    addi  $t2 $t2 4
    b     L3
L4: sw    $t7 ($t5)
    sw    $t5 4($t3)
    lw    $t2 4($t3)
    li    $t1 1
    lw    $t0 ($t2)
    slt   $t0 $t1 $t0
    bne   $t0 1   L5
    slti  $t0 $t1 0
    bne   $t0 1   L6
L5: j     _error
L6: addi  $sp $sp -4
    sw    $v0 ($sp)
    li    $v0 4
    mul   $t1 $t1 $v0
    lw    $v0 ($sp)
    addi  $sp $sp 4
    add   $t1 $t2 $t1
    li    $t0 40
    sw    $t0 4($t1)
    lw    $t2 4($t3)
    li    $t0 3
    addi  $sp $sp -4
    sw    $v0 ($sp)
    li    $v0 4
    mul   $t0 $t0 $v0
    lw    $v0 ($sp)
    addi  $sp $sp 4
    addi  $t0 $t0 -15
    addi  $t1 $t0 4
    lw    $t0 ($t2)
    slt   $t0 $t1 $t0
    bne   $t0 1   L7
    slti  $t0 $t1 0
    bne   $t0 1   L8
L7: j     _error
L8: addi  $sp $sp -4
    sw    $v0 ($sp)
    li    $v0 4
    mul   $t1 $t1 $v0
    lw    $v0 ($sp)
    addi  $sp $sp 4
    add   $t1 $t2 $t1
    li    $t0 80
    sw    $t0 4($t1)
    lw    $t2 4($t3)
    li    $t1 1
    lw    $t0 ($t2)
    slt   $t0 $t1 $t0
    bne   $t0 1   L9
    slti  $t0 $t1 0
    bne   $t0 1   L10
L9: j     _error
L10:addi  $sp $sp -4
    sw    $v0 ($sp)
    li    $v0 4
    mul   $t0 $t1 $v0
    lw    $v0 ($sp)
    addi  $sp $sp 4
    add   $t0 $t2 $t0
    lw    $t0 4($t0)
    move  $v0 $t0
    lw    $ra -4($fp)
    lw    $fp -8($fp)
    addi  $sp $sp 8
    jr    $ra

    .text
    .globl  _halloc
_halloc:
    li    $v0 9
    syscall
    jr    $ra

    .text
    .globl  _print
_print:
    li    $v0 1
    syscall
    la    $a0 endl
    li    $v0 4
    syscall
    jr    $ra

    .text
    .globl  _error
_error:
    la    $a0 strerr
    li    $v0 4
    syscall
    li    $v0 10
    syscall

    .data
    .align  0
endl:
    .asciiz "\n"

    .data
    .align  0
strerr:
    .asciiz "ERROR\n"

