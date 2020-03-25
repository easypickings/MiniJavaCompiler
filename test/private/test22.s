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
    li    $t4 16
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
L2: jal   B25
    move  $t0 $v0
    sw    $t0 ($s2)
    lw    $s1 ($s2)
    lw    $s1 ($s1)
    li    $t4 16
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
L4: jal   C25
    move  $t0 $v0
    sw    $t0 ($s0)
    li    $t0 1
    move  $a0 $s2
    move  $a1 $s0
    move  $a2 $t0
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
    .globl  A25_add_1
A25_add_1:
    sw    $ra -4($sp)
    sw    $fp -8($sp)
    move  $fp $sp
    addi  $sp $sp -8
    move  $t2 $a0
    move  $t1 $a2
    lw    $t0 ($t2)
    lw    $t0 4($t0)
    move  $a0 $t2
    move  $a1 $t1
    jalr  $t0
    move  $t0 $v0
    move  $v0 $t0
    lw    $ra -4($fp)
    lw    $fp -8($fp)
    addi  $sp $sp 8
    jr    $ra

    .text
    .globl  A25_init_2
A25_init_2:
    sw    $ra -4($sp)
    sw    $fp -8($sp)
    move  $fp $sp
    addi  $sp $sp -8
    move  $t2 $a0
    move  $t1 $a1
    slti  $t0 $t1 50
    bne   $t0 1   L5
    sw    $t1 8($t2)
    b     L6
L5: li    $t0 2
    mul   $t0 $t0 $t1
    sw    $t0 8($t2)
L6: lw    $t0 8($t2)
    move  $v0 $t0
    lw    $ra -4($fp)
    lw    $fp -8($fp)
    addi  $sp $sp 8
    jr    $ra

    .text
    .globl  B25_add_3
B25_add_3:
    sw    $ra -4($sp)
    sw    $fp -8($sp)
    move  $fp $sp
    addi  $sp $sp -16
    sw    $s0 -12($fp)
    sw    $s1 -16($fp)
    move  $s1 $a0
    move  $s0 $a1
    move  $t1 $a2
    lw    $t0 ($s1)
    lw    $t0 4($t0)
    move  $a0 $s1
    move  $a1 $t1
    jalr  $t0
    move  $t0 $v0
    move  $a0 $t0
    jal   _print
    lw    $t2 ($s0)
    lw    $t2 ($t2)
    lw    $t1 4($s1)
    lw    $t0 12($s1)
    move  $a0 $s0
    move  $a1 $t1
    move  $a2 $t0
    jalr  $t2
    move  $t0 $v0
    move  $v0 $t0
    lw    $s0 -12($fp)
    lw    $s1 -16($fp)
    lw    $ra -4($fp)
    lw    $fp -8($fp)
    addi  $sp $sp 16
    jr    $ra

    .text
    .globl  B25_init_4
B25_init_4:
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
L7: slt   $t0 $t2 $t4
    bne   $t0 1   L8
    sw    $t1 ($t3)
    addi  $t3 $t3 4
    addi  $t2 $t2 4
    b     L7
L8: jal   B25
    move  $t0 $v0
    sw    $t0 ($s0)
    sw    $s0 4($s2)
    li    $t0 2
    mul   $t0 $t0 $s1
    sw    $t0 8($s2)
    li    $t0 10
    add   $t0 $t0 $s1
    sw    $t0 12($s2)
    lw    $t1 8($s2)
    lw    $t0 12($s2)
    add   $t0 $t1 $t0
    move  $v0 $t0
    lw    $s0 -12($fp)
    lw    $s1 -16($fp)
    lw    $s2 -20($fp)
    lw    $ra -4($fp)
    lw    $fp -8($fp)
    addi  $sp $sp 20
    jr    $ra

    .text
    .globl  C25_add_5
C25_add_5:
    sw    $ra -4($sp)
    sw    $fp -8($sp)
    move  $fp $sp
    addi  $sp $sp -16
    sw    $s0 -12($fp)
    sw    $s1 -16($fp)
    move  $s1 $a0
    move  $s0 $a1
    move  $t1 $a2
    lw    $t0 ($s1)
    lw    $t0 4($t0)
    move  $a0 $s1
    move  $a1 $t1
    jalr  $t0
    move  $t0 $v0
    move  $a0 $t0
    jal   _print
    lw    $t2 4($s1)
    lw    $t1 ($t2)
    lw    $t1 ($t1)
    lw    $t0 12($s1)
    move  $a0 $t2
    move  $a1 $s0
    move  $a2 $t0
    jalr  $t1
    move  $t0 $v0
    move  $v0 $t0
    lw    $s0 -12($fp)
    lw    $s1 -16($fp)
    lw    $ra -4($fp)
    lw    $fp -8($fp)
    addi  $sp $sp 16
    jr    $ra

    .text
    .globl  C25_init_6
C25_init_6:
    sw    $ra -4($sp)
    sw    $fp -8($sp)
    move  $fp $sp
    addi  $sp $sp -20
    sw    $s0 -12($fp)
    sw    $s1 -16($fp)
    sw    $s2 -20($fp)
    move  $s2 $a0
    move  $s1 $a1
    li    $t4 12
    move  $a0 $t4
    jal   _halloc
    move  $s0 $v0
    move  $t3 $s0
    li    $t2 0
    li    $t1 0
L9: slt   $t0 $t2 $t4
    bne   $t0 1   L10
    sw    $t1 ($t3)
    addi  $t3 $t3 4
    addi  $t2 $t2 4
    b     L9
L10:jal   A25
    move  $t0 $v0
    sw    $t0 ($s0)
    sw    $s0 4($s2)
    addi  $t0 $s1 -5
    sw    $t0 8($s2)
    lw    $t0 8($s2)
    mul   $t0 $t0 $s1
    sw    $t0 12($s2)
    lw    $t1 12($s2)
    lw    $t0 8($s2)
    sub   $t0 $t1 $t0
    move  $v0 $t0
    lw    $s0 -12($fp)
    lw    $s1 -16($fp)
    lw    $s2 -20($fp)
    lw    $ra -4($fp)
    lw    $fp -8($fp)
    addi  $sp $sp 20
    jr    $ra

    .text
    .globl  A25
A25:
    sw    $ra -4($sp)
    sw    $fp -8($sp)
    move  $fp $sp
    addi  $sp $sp -8
    li    $a0 8
    jal   _halloc
    move  $t1 $v0
    la    $t0 A25_add_1
    sw    $t0 ($t1)
    la    $t0 A25_init_2
    sw    $t0 4($t1)
    move  $v0 $t1
    lw    $ra -4($fp)
    lw    $fp -8($fp)
    addi  $sp $sp 8
    jr    $ra

    .text
    .globl  B25
B25:
    sw    $ra -4($sp)
    sw    $fp -8($sp)
    move  $fp $sp
    addi  $sp $sp -8
    li    $a0 8
    jal   _halloc
    move  $t1 $v0
    la    $t0 B25_add_3
    sw    $t0 ($t1)
    la    $t0 B25_init_4
    sw    $t0 4($t1)
    move  $v0 $t1
    lw    $ra -4($fp)
    lw    $fp -8($fp)
    addi  $sp $sp 8
    jr    $ra

    .text
    .globl  C25
C25:
    sw    $ra -4($sp)
    sw    $fp -8($sp)
    move  $fp $sp
    addi  $sp $sp -8
    li    $a0 8
    jal   _halloc
    move  $t1 $v0
    la    $t0 C25_add_5
    sw    $t0 ($t1)
    la    $t0 C25_init_6
    sw    $t0 4($t1)
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

