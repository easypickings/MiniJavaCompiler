    .text
    .globl  main
main:
    sw    $ra -4($sp)
    sw    $fp -8($sp)
    move  $fp $sp
    addi  $sp $sp -20
    sw    $s0 -12($fp)
    sw    $s1 -16($fp)
    sw    $s2 -20($fp)
    li    $t4 32
    move  $a0 $t4
    jal   _halloc
    move  $s2 $v0
    move  $t3 $s2
    li    $t2 0
    li    $t1 0
L1: slt   $t0 $t2 $t4
    bne   $t0 1   L2
    sw    $t1 ($t3)
    addi  $t3 $t3 4
    addi  $t2 $t2 4
    b     L1
L2: jal   C23
    move  $t0 $v0
    sw    $t0 ($s2)
    lw    $s1 ($s2)
    lw    $s1 ($s1)
    li    $t4 24
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
L4: jal   B23
    move  $t0 $v0
    sw    $t0 ($s0)
    move  $a0 $s2
    move  $a1 $s0
    jalr  $s1
    move  $t0 $v0
    move  $a0 $t0
    jal   _print
    lw    $s0 -12($fp)
    lw    $s1 -16($fp)
    lw    $s2 -20($fp)
    lw    $ra -4($fp)
    lw    $fp -8($fp)
    addi  $sp $sp 20
    jr    $ra

    .text
    .globl  A23_init_1
A23_init_1:
    sw    $ra -4($sp)
    sw    $fp -8($sp)
    move  $fp $sp
    addi  $sp $sp -12
    sw    $s0 -12($fp)
    move  $s0 $a0
    move  $t1 $a1
    lw    $t0 ($t1)
    lw    $t0 4($t0)
    move  $a0 $t1
    jalr  $t0
    move  $t0 $v0
    sw    $t0 8($s0)
    li    $t0 222
    sw    $t0 12($s0)
    lw    $t2 ($s0)
    lw    $t2 8($t2)
    lw    $t1 8($s0)
    lw    $t0 12($s0)
    add   $t0 $t1 $t0
    move  $a0 $s0
    move  $a1 $t0
    jalr  $t2
    move  $t0 $v0
    sw    $t0 4($s0)
    lw    $t0 4($s0)
    move  $v0 $t0
    lw    $s0 -12($fp)
    lw    $ra -4($fp)
    lw    $fp -8($fp)
    addi  $sp $sp 12
    jr    $ra

    .text
    .globl  A23_getI1_2
A23_getI1_2:
    sw    $ra -4($sp)
    sw    $fp -8($sp)
    move  $fp $sp
    addi  $sp $sp -8
    move  $t0 $a0
    lw    $t0 4($t0)
    move  $v0 $t0
    lw    $ra -4($fp)
    lw    $fp -8($fp)
    addi  $sp $sp 8
    jr    $ra

    .text
    .globl  A23_setI1_3
A23_setI1_3:
    sw    $ra -4($sp)
    sw    $fp -8($sp)
    move  $fp $sp
    addi  $sp $sp -8
    move  $t0 $a1
    move  $v0 $t0
    lw    $ra -4($fp)
    lw    $fp -8($fp)
    addi  $sp $sp 8
    jr    $ra

    .text
    .globl  B23_init_4
B23_init_4:
    sw    $ra -4($sp)
    sw    $fp -8($sp)
    move  $fp $sp
    addi  $sp $sp -20
    sw    $s0 -12($fp)
    sw    $s1 -16($fp)
    sw    $s2 -20($fp)
    move  $s2 $a0
    move  $s1 $a1
    li    $t4 16
    move  $a0 $t4
    jal   _halloc
    move  $s0 $v0
    move  $t3 $s0
    li    $t2 0
    li    $t1 0
L5: slt   $t0 $t2 $t4
    bne   $t0 1   L6
    sw    $t1 ($t3)
    addi  $t3 $t3 4
    addi  $t2 $t2 4
    b     L5
L6: jal   A23
    move  $t0 $v0
    sw    $t0 ($s0)
    lw    $t0 ($s1)
    lw    $t0 4($t0)
    move  $a0 $s1
    jalr  $t0
    move  $t0 $v0
    sw    $t0 20($s2)
    lw    $t1 ($s2)
    lw    $t1 8($t1)
    lw    $t0 20($s2)
    move  $a0 $s2
    move  $a1 $t0
    jalr  $t1
    move  $t0 $v0
    sw    $t0 16($s2)
    lw    $t0 ($s0)
    lw    $t0 ($t0)
    move  $a0 $s0
    move  $a1 $s2
    jalr  $t0
    move  $t0 $v0
    move  $v0 $t0
    lw    $s0 -12($fp)
    lw    $s1 -16($fp)
    lw    $s2 -20($fp)
    lw    $ra -4($fp)
    lw    $fp -8($fp)
    addi  $sp $sp 20
    jr    $ra

    .text
    .globl  B23_getI1_5
B23_getI1_5:
    sw    $ra -4($sp)
    sw    $fp -8($sp)
    move  $fp $sp
    addi  $sp $sp -8
    move  $t0 $a0
    lw    $t0 16($t0)
    move  $v0 $t0
    lw    $ra -4($fp)
    lw    $fp -8($fp)
    addi  $sp $sp 8
    jr    $ra

    .text
    .globl  B23_setI1_6
B23_setI1_6:
    sw    $ra -4($sp)
    sw    $fp -8($sp)
    move  $fp $sp
    addi  $sp $sp -8
    move  $t0 $a1
    addi  $t0 $t0 111
    move  $v0 $t0
    lw    $ra -4($fp)
    lw    $fp -8($fp)
    addi  $sp $sp 8
    jr    $ra

    .text
    .globl  C23_init_7
C23_init_7:
    sw    $ra -4($sp)
    sw    $fp -8($sp)
    move  $fp $sp
    addi  $sp $sp -16
    sw    $s0 -12($fp)
    sw    $s1 -16($fp)
    move  $s1 $a0
    move  $s0 $a1
    li    $t0 333
    sw    $t0 28($s1)
    lw    $t1 ($s1)
    lw    $t1 8($t1)
    lw    $t0 28($s1)
    move  $a0 $s1
    move  $a1 $t0
    jalr  $t1
    move  $t0 $v0
    sw    $t0 24($s1)
    lw    $t0 ($s0)
    lw    $t0 ($t0)
    move  $a0 $s0
    move  $a1 $s1
    jalr  $t0
    move  $t0 $v0
    move  $v0 $t0
    lw    $s0 -12($fp)
    lw    $s1 -16($fp)
    lw    $ra -4($fp)
    lw    $fp -8($fp)
    addi  $sp $sp 16
    jr    $ra

    .text
    .globl  C23_getI1_8
C23_getI1_8:
    sw    $ra -4($sp)
    sw    $fp -8($sp)
    move  $fp $sp
    addi  $sp $sp -8
    move  $t0 $a0
    lw    $t0 24($t0)
    move  $v0 $t0
    lw    $ra -4($fp)
    lw    $fp -8($fp)
    addi  $sp $sp 8
    jr    $ra

    .text
    .globl  C23_setI1_9
C23_setI1_9:
    sw    $ra -4($sp)
    sw    $fp -8($sp)
    move  $fp $sp
    addi  $sp $sp -8
    move  $t0 $a1
    addi  $sp $sp -4
    sw    $v0 ($sp)
    li    $v0 2
    mul   $t0 $t0 $v0
    lw    $v0 ($sp)
    addi  $sp $sp 4
    move  $v0 $t0
    lw    $ra -4($fp)
    lw    $fp -8($fp)
    addi  $sp $sp 8
    jr    $ra

    .text
    .globl  A23
A23:
    sw    $ra -4($sp)
    sw    $fp -8($sp)
    move  $fp $sp
    addi  $sp $sp -8
    li    $a0 12
    jal   _halloc
    move  $t1 $v0
    la    $t0 A23_init_1
    sw    $t0 ($t1)
    la    $t0 A23_getI1_2
    sw    $t0 4($t1)
    la    $t0 A23_setI1_3
    sw    $t0 8($t1)
    move  $v0 $t1
    lw    $ra -4($fp)
    lw    $fp -8($fp)
    addi  $sp $sp 8
    jr    $ra

    .text
    .globl  B23
B23:
    sw    $ra -4($sp)
    sw    $fp -8($sp)
    move  $fp $sp
    addi  $sp $sp -8
    li    $a0 12
    jal   _halloc
    move  $t1 $v0
    la    $t0 B23_init_4
    sw    $t0 ($t1)
    la    $t0 B23_getI1_5
    sw    $t0 4($t1)
    la    $t0 B23_setI1_6
    sw    $t0 8($t1)
    move  $v0 $t1
    lw    $ra -4($fp)
    lw    $fp -8($fp)
    addi  $sp $sp 8
    jr    $ra

    .text
    .globl  C23
C23:
    sw    $ra -4($sp)
    sw    $fp -8($sp)
    move  $fp $sp
    addi  $sp $sp -8
    li    $a0 12
    jal   _halloc
    move  $t1 $v0
    la    $t0 C23_init_7
    sw    $t0 ($t1)
    la    $t0 C23_getI1_8
    sw    $t0 4($t1)
    la    $t0 C23_setI1_9
    sw    $t0 8($t1)
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

