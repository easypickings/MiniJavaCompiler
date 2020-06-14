    .text
    .globl  main
main:
    sw    $ra -4($sp)
    sw    $fp -8($sp)
    move  $fp $sp
    addi  $sp $sp -12
    sw    $s0 -12($fp)
    li    $t4 20
    move  $a0 $t4
    jal   _halloc
    move  $s0 $v0
    move  $t3 $s0
    li    $t2 0
    li    $t1 0
L1: slt   $t0 $t2 $t4
    bne   $t0 1   L2
    sw    $t1 ($t3)
    addi  $t3 $t3 4
    addi  $t2 $t2 4
    b     L1
L2: jal   A24
    move  $t0 $v0
    sw    $t0 ($s0)
    li    $t0 0
    move  $a0 $s0
    move  $a1 $t0
    jal   A24_m1_1
    move  $t0 $v0
    move  $a0 $t0
    jal   _print
    lw    $s0 -12($fp)
    lw    $ra -4($fp)
    lw    $fp -8($fp)
    addi  $sp $sp 12
    jr    $ra

    .text
    .globl  A24_m1_1
A24_m1_1:
    sw    $ra -4($sp)
    sw    $fp -8($sp)
    move  $fp $sp
    addi  $sp $sp -16
    sw    $s0 -12($fp)
    sw    $s1 -16($fp)
    move  $s1 $a0
    move  $t0 $a1
    bne   $t0 1   L3
    li    $t6 5
    b     L4
L3: li    $t6 10
L4: addi  $sp $sp -4
    sw    $v0 ($sp)
    li    $v0 4
    mul   $t5 $t6 $v0
    lw    $v0 ($sp)
    addi  $sp $sp 4
    addi  $t5 $t5 4
    move  $a0 $t5
    jal   _halloc
    move  $t4 $v0
    move  $t3 $t4
    li    $t2 0
    li    $t1 0
L5: slt   $t0 $t2 $t5
    bne   $t0 1   L6
    sw    $t1 ($t3)
    addi  $t3 $t3 4
    addi  $t2 $t2 4
    b     L5
L6: sw    $t6 ($t4)
    sw    $t4 4($s1)
    li    $t6 0
    lw    $t0 4($s1)
    lw    $t0 ($t0)
    move  $t3 $t0
L7: slt   $t0 $t6 $t3
    bne   $t0 1   L10
    lw    $t2 4($s1)
    lw    $t0 ($t2)
    slt   $t0 $t6 $t0
    bne   $t0 1   L8
    slti  $t0 $t6 0
    bne   $t0 1   L9
L8: j     _error
L9: addi  $sp $sp -4
    sw    $v0 ($sp)
    li    $v0 4
    mul   $t1 $t6 $v0
    lw    $v0 ($sp)
    addi  $sp $sp 4
    add   $t1 $t2 $t1
    addi  $t0 $t6 1
    sw    $t0 4($t1)
    addi  $t0 $t6 1
    move  $t6 $t0
    b     L7
L10:li    $t4 20
    move  $a0 $t4
    jal   _halloc
    move  $s0 $v0
    move  $t3 $s0
    li    $t2 0
    li    $t1 0
L11:slt   $t0 $t2 $t4
    bne   $t0 1   L12
    sw    $t1 ($t3)
    addi  $t3 $t3 4
    addi  $t2 $t2 4
    b     L11
L12:jal   B24
    move  $t0 $v0
    sw    $t0 ($s0)
    sw    $s0 12($s1)
    lw    $t0 12($s1)
    sw    $t0 8($s1)
    lw    $t2 8($s1)
    lw    $t1 ($t2)
    lw    $t1 ($t1)
    lw    $t0 4($s1)
    move  $a0 $t2
    move  $a1 $t0
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
    .globl  A24_m2_2
A24_m2_2:
    sw    $ra -4($sp)
    sw    $fp -8($sp)
    move  $fp $sp
    addi  $sp $sp -8
    li    $v0 1
    lw    $ra -4($fp)
    lw    $fp -8($fp)
    addi  $sp $sp 8
    jr    $ra

    .text
    .globl  B24_m2_3
B24_m2_3:
    sw    $ra -4($sp)
    sw    $fp -8($sp)
    move  $fp $sp
    addi  $sp $sp -8
    move  $t4 $a1
    li    $t1 0
    li    $t2 0
    lw    $t0 ($t4)
    move  $t3 $t0
L13:slt   $t0 $t1 $t3
    bne   $t0 1   L16
    lw    $t0 ($t4)
    slt   $t0 $t1 $t0
    bne   $t0 1   L14
    slti  $t0 $t1 0
    bne   $t0 1   L15
L14:j     _error
L15:addi  $sp $sp -4
    sw    $v0 ($sp)
    li    $v0 4
    mul   $t0 $t1 $v0
    lw    $v0 ($sp)
    addi  $sp $sp 4
    add   $t0 $t4 $t0
    lw    $t0 4($t0)
    add   $t0 $t2 $t0
    move  $t2 $t0
    addi  $t0 $t1 1
    move  $t1 $t0
    b     L13
L16:move  $v0 $t2
    lw    $ra -4($fp)
    lw    $fp -8($fp)
    addi  $sp $sp 8
    jr    $ra

    .text
    .globl  A24
A24:
    sw    $ra -4($sp)
    sw    $fp -8($sp)
    move  $fp $sp
    addi  $sp $sp -8
    li    $a0 4
    jal   _halloc
    move  $t1 $v0
    la    $t0 A24_m2_2
    sw    $t0 ($t1)
    move  $v0 $t1
    lw    $ra -4($fp)
    lw    $fp -8($fp)
    addi  $sp $sp 8
    jr    $ra

    .text
    .globl  B24
B24:
    sw    $ra -4($sp)
    sw    $fp -8($sp)
    move  $fp $sp
    addi  $sp $sp -8
    li    $a0 4
    jal   _halloc
    move  $t1 $v0
    la    $t0 B24_m2_3
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

