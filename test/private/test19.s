    .text
    .globl  main
main:
    sw    $ra -4($sp)
    sw    $fp -8($sp)
    move  $fp $sp
    addi  $sp $sp -16
    sw    $s0 -12($fp)
    sw    $s1 -16($fp)
    li    $t4 12
    move  $a0 $t4
    jal   _halloc
    move  $s1 $v0
    move  $t3 $s1
    li    $t2 0
    li    $t1 0
L1: slt   $t0 $t2 $t4
    bne   $t0 1   L2
    sw    $t1 ($t3)
    addi  $t3 $t3 4
    addi  $t2 $t2 4
    b     L1
L2: jal   B22
    move  $t0 $v0
    sw    $t0 ($s1)
    li    $t4 12
    move  $a0 $t4
    jal   _halloc
    move  $s0 $v0
    move  $t3 $s0
    li    $t2 0
    li    $t1 0
L3: slt   $t0 $t2 $t4
    bne   $t0 1   L4
    sw    $t1 ($t3)
    addi  $t3 $t3 4
    addi  $t2 $t2 4
    b     L3
L4: jal   B22
    move  $t0 $v0
    sw    $t0 ($s0)
    move  $a0 $s1
    move  $a1 $s0
    jal   B22_go_2
    move  $t0 $v0
    move  $a0 $t0
    jal   _print
    lw    $s0 -12($fp)
    lw    $s1 -16($fp)
    lw    $ra -4($fp)
    lw    $fp -8($fp)
    addi  $sp $sp 16
    jr    $ra

    .text
    .globl  A22_getI1_1
A22_getI1_1:
    sw    $ra -4($sp)
    sw    $fp -8($sp)
    move  $fp $sp
    addi  $sp $sp -8
    move  $t1 $a0
    li    $t0 123
    sw    $t0 4($t1)
    lw    $t0 4($t1)
    move  $v0 $t0
    lw    $ra -4($fp)
    lw    $fp -8($fp)
    addi  $sp $sp 8
    jr    $ra

    .text
    .globl  B22_go_2
B22_go_2:
    sw    $ra -4($sp)
    sw    $fp -8($sp)
    move  $fp $sp
    addi  $sp $sp -8
    move  $t2 $a1
    lw    $t1 ($t2)
    lw    $t1 ($t1)
    li    $t0 1
    move  $a0 $t2
    move  $a1 $t0
    jalr  $t1
    move  $t0 $v0
    move  $v0 $t0
    lw    $ra -4($fp)
    lw    $fp -8($fp)
    addi  $sp $sp 8
    jr    $ra

    .text
    .globl  B22_getI1_3
B22_getI1_3:
    sw    $ra -4($sp)
    sw    $fp -8($sp)
    move  $fp $sp
    addi  $sp $sp -8
    move  $t1 $a0
    move  $t0 $a1
    bne   $t0 1   L5
    li    $t0 456
    sw    $t0 4($t1)
    b     L6
L5: li    $t0 789
    sw    $t0 4($t1)
L6: lw    $t0 4($t1)
    move  $v0 $t0
    lw    $ra -4($fp)
    lw    $fp -8($fp)
    addi  $sp $sp 8
    jr    $ra

    .text
    .globl  A22
A22:
    sw    $ra -4($sp)
    sw    $fp -8($sp)
    move  $fp $sp
    addi  $sp $sp -8
    li    $a0 4
    jal   _halloc
    move  $t1 $v0
    la    $t0 A22_getI1_1
    sw    $t0 ($t1)
    move  $v0 $t1
    lw    $ra -4($fp)
    lw    $fp -8($fp)
    addi  $sp $sp 8
    jr    $ra

    .text
    .globl  B22
B22:
    sw    $ra -4($sp)
    sw    $fp -8($sp)
    move  $fp $sp
    addi  $sp $sp -8
    li    $a0 4
    jal   _halloc
    move  $t1 $v0
    la    $t0 B22_getI1_3
    sw    $t0 ($t1)
    move  $v0 $t1
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

