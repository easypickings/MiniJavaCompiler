    .text
    .globl  main
main:
    sw    $ra -4($sp)
    sw    $fp -8($sp)
    move  $fp $sp
    addi  $sp $sp -8
    li    $t4 12
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
L2: li    $t0 10
    move  $a0 $t5
    move  $a1 $t0
    jal   LS_Start_1
    move  $t0 $v0
    move  $a0 $t0
    jal   _print
    lw    $ra -4($fp)
    lw    $fp -8($fp)
    addi  $sp $sp 8
    jr    $ra

    .text
    .globl  LS_Start_1
LS_Start_1:
    sw    $ra -4($sp)
    sw    $fp -8($sp)
    move  $fp $sp
    addi  $sp $sp -12
    sw    $s0 -12($fp)
    move  $s0 $a0
    move  $t0 $a1
    move  $a0 $s0
    move  $a1 $t0
    jal   LS_Init_4
    move  $a0 $s0
    jal   LS_Print_2
    li    $a0 9999
    jal   _print
    li    $t0 8
    move  $a0 $s0
    move  $a1 $t0
    jal   LS_Search_3
    move  $t0 $v0
    move  $a0 $t0
    jal   _print
    li    $t0 12
    move  $a0 $s0
    move  $a1 $t0
    jal   LS_Search_3
    move  $t0 $v0
    move  $a0 $t0
    jal   _print
    li    $t0 17
    move  $a0 $s0
    move  $a1 $t0
    jal   LS_Search_3
    move  $t0 $v0
    move  $a0 $t0
    jal   _print
    li    $t0 50
    move  $a0 $s0
    move  $a1 $t0
    jal   LS_Search_3
    move  $t0 $v0
    move  $a0 $t0
    jal   _print
    li    $v0 55
    lw    $s0 -12($fp)
    lw    $ra -4($fp)
    lw    $fp -8($fp)
    addi  $sp $sp 12
    jr    $ra

    .text
    .globl  LS_Print_2
LS_Print_2:
    sw    $ra -4($sp)
    sw    $fp -8($sp)
    move  $fp $sp
    addi  $sp $sp -8
    move  $t3 $a0
    li    $t2 1
L3: lw    $t0 8($t3)
    slt   $t0 $t2 $t0
    bne   $t0 1   L6
    lw    $t1 4($t3)
    lw    $t0 ($t1)
    slt   $t0 $t2 $t0
    bne   $t0 1   L4
    slti  $t0 $t2 0
    bne   $t0 1   L5
L4: j     _error
L5: addi  $sp $sp -4
    sw    $v0 ($sp)
    li    $v0 4
    mul   $t0 $t2 $v0
    lw    $v0 ($sp)
    addi  $sp $sp 4
    add   $t0 $t1 $t0
    lw    $t0 4($t0)
    move  $a0 $t0
    jal   _print
    addi  $t0 $t2 1
    move  $t2 $t0
    b     L3
L6: li    $v0 0
    lw    $ra -4($fp)
    lw    $fp -8($fp)
    addi  $sp $sp 8
    jr    $ra

    .text
    .globl  LS_Search_3
LS_Search_3:
    sw    $ra -4($sp)
    sw    $fp -8($sp)
    move  $fp $sp
    addi  $sp $sp -8
    move  $t6 $a0
    move  $t5 $a1
    li    $t4 1
    li    $t3 0
L7: lw    $t0 8($t6)
    slt   $t0 $t4 $t0
    bne   $t0 1   L14
    lw    $t1 4($t6)
    lw    $t0 ($t1)
    slt   $t0 $t4 $t0
    bne   $t0 1   L8
    slti  $t0 $t4 0
    bne   $t0 1   L9
L8: j     _error
L9: addi  $sp $sp -4
    sw    $v0 ($sp)
    li    $v0 4
    mul   $t0 $t4 $v0
    lw    $v0 ($sp)
    addi  $sp $sp 4
    add   $t0 $t1 $t0
    lw    $t0 4($t0)
    move  $t2 $t0
    addi  $t0 $t5 1
    move  $t1 $t0
    slt   $t0 $t2 $t5
    bne   $t0 1   L10
    b     L13
L10:slt   $t0 $t2 $t1
    slti  $t0 $t0 1
    bne   $t0 1   L11
    b     L13
L11:li    $t3 1
    lw    $t0 8($t6)
    move  $t4 $t0
L13:addi  $t0 $t4 1
    move  $t4 $t0
    b     L7
L14:move  $v0 $t3
    lw    $ra -4($fp)
    lw    $fp -8($fp)
    addi  $sp $sp 8
    jr    $ra

    .text
    .globl  LS_Init_4
LS_Init_4:
    sw    $ra -4($sp)
    sw    $fp -8($sp)
    move  $fp $sp
    addi  $sp $sp -8
    move  $t3 $a0
    move  $t7 $a1
    sw    $t7 8($t3)
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
L15:slt   $t0 $t2 $t6
    bne   $t0 1   L16
    sw    $t1 ($t4)
    addi  $t4 $t4 4
    addi  $t2 $t2 4
    b     L15
L16:sw    $t7 ($t5)
    sw    $t5 4($t3)
    li    $t7 1
    lw    $t0 8($t3)
    addi  $t0 $t0 1
    move  $t6 $t0
L17:lw    $t0 8($t3)
    slt   $t0 $t7 $t0
    bne   $t0 1   L20
    li    $t0 2
    mul   $t0 $t0 $t7
    move  $t5 $t0
    addi  $t0 $t6 -3
    move  $t4 $t0
    lw    $t2 4($t3)
    lw    $t0 ($t2)
    slt   $t0 $t7 $t0
    bne   $t0 1   L18
    slti  $t0 $t7 0
    bne   $t0 1   L19
L18:j     _error
L19:addi  $sp $sp -4
    sw    $v0 ($sp)
    li    $v0 4
    mul   $t1 $t7 $v0
    lw    $v0 ($sp)
    addi  $sp $sp 4
    add   $t1 $t2 $t1
    add   $t0 $t5 $t4
    sw    $t0 4($t1)
    addi  $t0 $t7 1
    move  $t7 $t0
    addi  $t0 $t6 -1
    move  $t6 $t0
    b     L17
L20:li    $v0 0
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

