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
    jal   BBS_Start_1
    move  $t0 $v0
    move  $a0 $t0
    jal   _print
    lw    $ra -4($fp)
    lw    $fp -8($fp)
    addi  $sp $sp 8
    jr    $ra

    .text
    .globl  BBS_Start_1
BBS_Start_1:
    sw    $ra -4($sp)
    sw    $fp -8($sp)
    move  $fp $sp
    addi  $sp $sp -12
    sw    $s0 -12($fp)
    move  $s0 $a0
    move  $t0 $a1
    move  $a0 $s0
    move  $a1 $t0
    jal   BBS_Init_4
    move  $a0 $s0
    jal   BBS_Print_3
    li    $a0 99999
    jal   _print
    move  $a0 $s0
    jal   BBS_Sort_2
    move  $a0 $s0
    jal   BBS_Print_3
    li    $v0 0
    lw    $s0 -12($fp)
    lw    $ra -4($fp)
    lw    $fp -8($fp)
    addi  $sp $sp 12
    jr    $ra

    .text
    .globl  BBS_Sort_2
BBS_Sort_2:
    sw    $ra -4($sp)
    sw    $fp -8($sp)
    move  $fp $sp
    addi  $sp $sp -8
    move  $t4 $a0
    lw    $t0 8($t4)
    addi  $t0 $t0 -1
    move  $t5 $t0
    li    $t0 0
    addi  $t0 $t0 -1
    move  $t3 $t0
L3: slt   $t0 $t3 $t5
    bne   $t0 1   L20
    li    $t6 1
L4: addi  $t0 $t5 1
    slt   $t0 $t6 $t0
    bne   $t0 1   L19
    addi  $t0 $t6 -1
    move  $t2 $t0
    lw    $t1 4($t4)
    lw    $t0 ($t1)
    slt   $t0 $t2 $t0
    bne   $t0 1   L5
    slti  $t0 $t2 0
    bne   $t0 1   L6
L5: j     _error
L6: addi  $sp $sp -4
    sw    $v0 ($sp)
    li    $v0 4
    mul   $t0 $t2 $v0
    lw    $v0 ($sp)
    addi  $sp $sp 4
    add   $t0 $t1 $t0
    lw    $t0 4($t0)
    move  $t2 $t0
    lw    $t1 4($t4)
    lw    $t0 ($t1)
    slt   $t0 $t6 $t0
    bne   $t0 1   L7
    slti  $t0 $t6 0
    bne   $t0 1   L8
L7: j     _error
L8: addi  $sp $sp -4
    sw    $v0 ($sp)
    li    $v0 4
    mul   $t0 $t6 $v0
    lw    $v0 ($sp)
    addi  $sp $sp 4
    add   $t0 $t1 $t0
    lw    $t0 4($t0)
    slt   $t0 $t0 $t2
    bne   $t0 1   L18
    addi  $t0 $t6 -1
    move  $t2 $t0
    lw    $t1 4($t4)
    lw    $t0 ($t1)
    slt   $t0 $t2 $t0
    bne   $t0 1   L9
    slti  $t0 $t2 0
    bne   $t0 1   L10
L9: j     _error
L10:addi  $sp $sp -4
    sw    $v0 ($sp)
    li    $v0 4
    mul   $t0 $t2 $v0
    lw    $v0 ($sp)
    addi  $sp $sp 4
    add   $t0 $t1 $t0
    lw    $t0 4($t0)
    move  $t7 $t0
    lw    $t1 4($t4)
    lw    $t0 ($t1)
    slt   $t0 $t2 $t0
    bne   $t0 1   L11
    slti  $t0 $t2 0
    bne   $t0 1   L12
L11:j     _error
L12:addi  $sp $sp -4
    sw    $v0 ($sp)
    li    $v0 4
    mul   $t2 $t2 $v0
    lw    $v0 ($sp)
    addi  $sp $sp 4
    add   $t2 $t1 $t2
    lw    $t1 4($t4)
    lw    $t0 ($t1)
    slt   $t0 $t6 $t0
    bne   $t0 1   L13
    slti  $t0 $t6 0
    bne   $t0 1   L14
L13:j     _error
L14:addi  $sp $sp -4
    sw    $v0 ($sp)
    li    $v0 4
    mul   $t0 $t6 $v0
    lw    $v0 ($sp)
    addi  $sp $sp 4
    add   $t0 $t1 $t0
    lw    $t0 4($t0)
    sw    $t0 4($t2)
    lw    $t1 4($t4)
    lw    $t0 ($t1)
    slt   $t0 $t6 $t0
    bne   $t0 1   L15
    slti  $t0 $t6 0
    bne   $t0 1   L16
L15:j     _error
L16:addi  $sp $sp -4
    sw    $v0 ($sp)
    li    $v0 4
    mul   $t0 $t6 $v0
    lw    $v0 ($sp)
    addi  $sp $sp 4
    add   $t0 $t1 $t0
    sw    $t7 4($t0)
    b     L18
L18:addi  $t0 $t6 1
    move  $t6 $t0
    b     L4
L19:addi  $t0 $t5 -1
    move  $t5 $t0
    b     L3
L20:li    $v0 0
    lw    $ra -4($fp)
    lw    $fp -8($fp)
    addi  $sp $sp 8
    jr    $ra

    .text
    .globl  BBS_Print_3
BBS_Print_3:
    sw    $ra -4($sp)
    sw    $fp -8($sp)
    move  $fp $sp
    addi  $sp $sp -8
    move  $t3 $a0
    li    $t2 0
L21:lw    $t0 8($t3)
    slt   $t0 $t2 $t0
    bne   $t0 1   L24
    lw    $t1 4($t3)
    lw    $t0 ($t1)
    slt   $t0 $t2 $t0
    bne   $t0 1   L22
    slti  $t0 $t2 0
    bne   $t0 1   L23
L22:j     _error
L23:addi  $sp $sp -4
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
    b     L21
L24:li    $v0 0
    lw    $ra -4($fp)
    lw    $fp -8($fp)
    addi  $sp $sp 8
    jr    $ra

    .text
    .globl  BBS_Init_4
BBS_Init_4:
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
L25:slt   $t0 $t2 $t6
    bne   $t0 1   L26
    sw    $t1 ($t4)
    addi  $t4 $t4 4
    addi  $t2 $t2 4
    b     L25
L26:sw    $t7 ($t5)
    sw    $t5 4($t3)
    lw    $t2 4($t3)
    li    $t1 0
    lw    $t0 ($t2)
    slt   $t0 $t1 $t0
    bne   $t0 1   L27
    slti  $t0 $t1 0
    bne   $t0 1   L28
L27:j     _error
L28:addi  $sp $sp -4
    sw    $v0 ($sp)
    li    $v0 4
    mul   $t1 $t1 $v0
    lw    $v0 ($sp)
    addi  $sp $sp 4
    add   $t1 $t2 $t1
    li    $t0 20
    sw    $t0 4($t1)
    lw    $t2 4($t3)
    li    $t1 1
    lw    $t0 ($t2)
    slt   $t0 $t1 $t0
    bne   $t0 1   L29
    slti  $t0 $t1 0
    bne   $t0 1   L30
L29:j     _error
L30:addi  $sp $sp -4
    sw    $v0 ($sp)
    li    $v0 4
    mul   $t1 $t1 $v0
    lw    $v0 ($sp)
    addi  $sp $sp 4
    add   $t1 $t2 $t1
    li    $t0 7
    sw    $t0 4($t1)
    lw    $t2 4($t3)
    li    $t1 2
    lw    $t0 ($t2)
    slt   $t0 $t1 $t0
    bne   $t0 1   L31
    slti  $t0 $t1 0
    bne   $t0 1   L32
L31:j     _error
L32:addi  $sp $sp -4
    sw    $v0 ($sp)
    li    $v0 4
    mul   $t1 $t1 $v0
    lw    $v0 ($sp)
    addi  $sp $sp 4
    add   $t1 $t2 $t1
    li    $t0 12
    sw    $t0 4($t1)
    lw    $t2 4($t3)
    li    $t1 3
    lw    $t0 ($t2)
    slt   $t0 $t1 $t0
    bne   $t0 1   L33
    slti  $t0 $t1 0
    bne   $t0 1   L34
L33:j     _error
L34:addi  $sp $sp -4
    sw    $v0 ($sp)
    li    $v0 4
    mul   $t1 $t1 $v0
    lw    $v0 ($sp)
    addi  $sp $sp 4
    add   $t1 $t2 $t1
    li    $t0 18
    sw    $t0 4($t1)
    lw    $t2 4($t3)
    li    $t1 4
    lw    $t0 ($t2)
    slt   $t0 $t1 $t0
    bne   $t0 1   L35
    slti  $t0 $t1 0
    bne   $t0 1   L36
L35:j     _error
L36:addi  $sp $sp -4
    sw    $v0 ($sp)
    li    $v0 4
    mul   $t1 $t1 $v0
    lw    $v0 ($sp)
    addi  $sp $sp 4
    add   $t1 $t2 $t1
    li    $t0 2
    sw    $t0 4($t1)
    lw    $t2 4($t3)
    li    $t1 5
    lw    $t0 ($t2)
    slt   $t0 $t1 $t0
    bne   $t0 1   L37
    slti  $t0 $t1 0
    bne   $t0 1   L38
L37:j     _error
L38:addi  $sp $sp -4
    sw    $v0 ($sp)
    li    $v0 4
    mul   $t1 $t1 $v0
    lw    $v0 ($sp)
    addi  $sp $sp 4
    add   $t1 $t2 $t1
    li    $t0 11
    sw    $t0 4($t1)
    lw    $t2 4($t3)
    li    $t1 6
    lw    $t0 ($t2)
    slt   $t0 $t1 $t0
    bne   $t0 1   L39
    slti  $t0 $t1 0
    bne   $t0 1   L40
L39:j     _error
L40:addi  $sp $sp -4
    sw    $v0 ($sp)
    li    $v0 4
    mul   $t1 $t1 $v0
    lw    $v0 ($sp)
    addi  $sp $sp 4
    add   $t1 $t2 $t1
    li    $t0 6
    sw    $t0 4($t1)
    lw    $t2 4($t3)
    li    $t1 7
    lw    $t0 ($t2)
    slt   $t0 $t1 $t0
    bne   $t0 1   L41
    slti  $t0 $t1 0
    bne   $t0 1   L42
L41:j     _error
L42:addi  $sp $sp -4
    sw    $v0 ($sp)
    li    $v0 4
    mul   $t1 $t1 $v0
    lw    $v0 ($sp)
    addi  $sp $sp 4
    add   $t1 $t2 $t1
    li    $t0 9
    sw    $t0 4($t1)
    lw    $t2 4($t3)
    li    $t1 8
    lw    $t0 ($t2)
    slt   $t0 $t1 $t0
    bne   $t0 1   L43
    slti  $t0 $t1 0
    bne   $t0 1   L44
L43:j     _error
L44:addi  $sp $sp -4
    sw    $v0 ($sp)
    li    $v0 4
    mul   $t1 $t1 $v0
    lw    $v0 ($sp)
    addi  $sp $sp 4
    add   $t1 $t2 $t1
    li    $t0 19
    sw    $t0 4($t1)
    lw    $t2 4($t3)
    li    $t1 9
    lw    $t0 ($t2)
    slt   $t0 $t1 $t0
    bne   $t0 1   L45
    slti  $t0 $t1 0
    bne   $t0 1   L46
L45:j     _error
L46:addi  $sp $sp -4
    sw    $v0 ($sp)
    li    $v0 4
    mul   $t1 $t1 $v0
    lw    $v0 ($sp)
    addi  $sp $sp 4
    add   $t1 $t2 $t1
    li    $t0 5
    sw    $t0 4($t1)
    li    $v0 0
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

