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
    jal   QS_Start_1
    move  $t0 $v0
    move  $a0 $t0
    jal   _print
    lw    $ra -4($fp)
    lw    $fp -8($fp)
    addi  $sp $sp 8
    jr    $ra

    .text
    .globl  QS_Start_1
QS_Start_1:
    sw    $ra -4($sp)
    sw    $fp -8($sp)
    move  $fp $sp
    addi  $sp $sp -12
    sw    $s0 -12($fp)
    move  $s0 $a0
    move  $t0 $a1
    move  $a0 $s0
    move  $a1 $t0
    jal   QS_Init_4
    move  $a0 $s0
    jal   QS_Print_3
    li    $a0 9999
    jal   _print
    lw    $t0 8($s0)
    addi  $t0 $t0 -1
    move  $t1 $t0
    li    $t0 0
    move  $a0 $s0
    move  $a1 $t0
    move  $a2 $t1
    jal   QS_Sort_2
    move  $a0 $s0
    jal   QS_Print_3
    li    $v0 0
    lw    $s0 -12($fp)
    lw    $ra -4($fp)
    lw    $fp -8($fp)
    addi  $sp $sp 12
    jr    $ra

    .text
    .globl  QS_Sort_2
QS_Sort_2:
    sw    $ra -4($sp)
    sw    $fp -8($sp)
    move  $fp $sp
    addi  $sp $sp -20
    sw    $s0 -12($fp)
    sw    $s1 -16($fp)
    sw    $s2 -20($fp)
    move  $s2 $a0
    move  $t3 $a1
    move  $s1 $a2
    li    $t4 0
    slt   $t0 $t3 $s1
    bne   $t0 1   L40
    lw    $t1 4($s2)
    lw    $t0 ($t1)
    slt   $t0 $s1 $t0
    bne   $t0 1   L3
    slti  $t0 $s1 0
    bne   $t0 1   L4
L3: j     _error
L4: addi  $sp $sp -4
    sw    $v0 ($sp)
    li    $v0 4
    mul   $t0 $s1 $v0
    lw    $v0 ($sp)
    addi  $sp $sp 4
    add   $t0 $t1 $t0
    lw    $t0 4($t0)
    move  $t6 $t0
    addi  $t0 $t3 -1
    move  $s0 $t0
    move  $t2 $s1
    li    $t0 1
L5: bne   $t0 1   L28
    li    $t0 1
L6: bne   $t0 1   L11
    addi  $t0 $s0 1
    move  $s0 $t0
    lw    $t1 4($s2)
    lw    $t0 ($t1)
    slt   $t0 $s0 $t0
    bne   $t0 1   L7
    slti  $t0 $s0 0
    bne   $t0 1   L8
L7: j     _error
L8: addi  $sp $sp -4
    sw    $v0 ($sp)
    li    $v0 4
    mul   $t0 $s0 $v0
    lw    $v0 ($sp)
    addi  $sp $sp 4
    add   $t0 $t1 $t0
    lw    $t0 4($t0)
    slt   $t0 $t0 $t6
    slti  $t0 $t0 1
    bne   $t0 1   L9
    li    $t0 0
    b     L6
L9: li    $t0 1
    b     L6
L11:li    $t0 1
L12:bne   $t0 1   L17
    addi  $t0 $t2 -1
    move  $t2 $t0
    lw    $t1 4($s2)
    lw    $t0 ($t1)
    slt   $t0 $t2 $t0
    bne   $t0 1   L13
    slti  $t0 $t2 0
    bne   $t0 1   L14
L13:j     _error
L14:addi  $sp $sp -4
    sw    $v0 ($sp)
    li    $v0 4
    mul   $t0 $t2 $v0
    lw    $v0 ($sp)
    addi  $sp $sp 4
    add   $t0 $t1 $t0
    lw    $t0 4($t0)
    slt   $t0 $t6 $t0
    slti  $t0 $t0 1
    bne   $t0 1   L15
    li    $t0 0
    b     L12
L15:li    $t0 1
    b     L12
L17:lw    $t1 4($s2)
    lw    $t0 ($t1)
    slt   $t0 $s0 $t0
    bne   $t0 1   L18
    slti  $t0 $s0 0
    bne   $t0 1   L19
L18:j     _error
L19:addi  $sp $sp -4
    sw    $v0 ($sp)
    li    $v0 4
    mul   $t0 $s0 $v0
    lw    $v0 ($sp)
    addi  $sp $sp 4
    add   $t0 $t1 $t0
    lw    $t0 4($t0)
    move  $t4 $t0
    lw    $t1 4($s2)
    lw    $t0 ($t1)
    slt   $t0 $s0 $t0
    bne   $t0 1   L20
    slti  $t0 $s0 0
    bne   $t0 1   L21
L20:j     _error
L21:addi  $sp $sp -4
    sw    $v0 ($sp)
    li    $v0 4
    mul   $t5 $s0 $v0
    lw    $v0 ($sp)
    addi  $sp $sp 4
    add   $t5 $t1 $t5
    lw    $t1 4($s2)
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
    sw    $t0 4($t5)
    lw    $t1 4($s2)
    lw    $t0 ($t1)
    slt   $t0 $t2 $t0
    bne   $t0 1   L24
    slti  $t0 $t2 0
    bne   $t0 1   L25
L24:j     _error
L25:addi  $sp $sp -4
    sw    $v0 ($sp)
    li    $v0 4
    mul   $t0 $t2 $v0
    lw    $v0 ($sp)
    addi  $sp $sp 4
    add   $t0 $t1 $t0
    sw    $t4 4($t0)
    addi  $t0 $s0 1
    slt   $t0 $t2 $t0
    bne   $t0 1   L26
    li    $t0 0
    b     L5
L26:li    $t0 1
    b     L5
L28:lw    $t1 4($s2)
    lw    $t0 ($t1)
    slt   $t0 $t2 $t0
    bne   $t0 1   L29
    slti  $t0 $t2 0
    bne   $t0 1   L30
L29:j     _error
L30:addi  $sp $sp -4
    sw    $v0 ($sp)
    li    $v0 4
    mul   $t2 $t2 $v0
    lw    $v0 ($sp)
    addi  $sp $sp 4
    add   $t2 $t1 $t2
    lw    $t1 4($s2)
    lw    $t0 ($t1)
    slt   $t0 $s0 $t0
    bne   $t0 1   L31
    slti  $t0 $s0 0
    bne   $t0 1   L32
L31:j     _error
L32:addi  $sp $sp -4
    sw    $v0 ($sp)
    li    $v0 4
    mul   $t0 $s0 $v0
    lw    $v0 ($sp)
    addi  $sp $sp 4
    add   $t0 $t1 $t0
    lw    $t0 4($t0)
    sw    $t0 4($t2)
    lw    $t1 4($s2)
    lw    $t0 ($t1)
    slt   $t0 $s0 $t0
    bne   $t0 1   L33
    slti  $t0 $s0 0
    bne   $t0 1   L34
L33:j     _error
L34:addi  $sp $sp -4
    sw    $v0 ($sp)
    li    $v0 4
    mul   $t2 $s0 $v0
    lw    $v0 ($sp)
    addi  $sp $sp 4
    add   $t2 $t1 $t2
    lw    $t1 4($s2)
    lw    $t0 ($t1)
    slt   $t0 $s1 $t0
    bne   $t0 1   L35
    slti  $t0 $s1 0
    bne   $t0 1   L36
L35:j     _error
L36:addi  $sp $sp -4
    sw    $v0 ($sp)
    li    $v0 4
    mul   $t0 $s1 $v0
    lw    $v0 ($sp)
    addi  $sp $sp 4
    add   $t0 $t1 $t0
    lw    $t0 4($t0)
    sw    $t0 4($t2)
    lw    $t1 4($s2)
    lw    $t0 ($t1)
    slt   $t0 $s1 $t0
    bne   $t0 1   L37
    slti  $t0 $s1 0
    bne   $t0 1   L38
L37:j     _error
L38:addi  $sp $sp -4
    sw    $v0 ($sp)
    li    $v0 4
    mul   $t0 $s1 $v0
    lw    $v0 ($sp)
    addi  $sp $sp 4
    add   $t0 $t1 $t0
    sw    $t4 4($t0)
    addi  $t0 $s0 -1
    move  $a0 $s2
    move  $a1 $t3
    move  $a2 $t0
    jal   QS_Sort_2
    addi  $t0 $s0 1
    move  $a0 $s2
    move  $a1 $t0
    move  $a2 $s1
    jal   QS_Sort_2
    b     L40
L40:li    $v0 0
    lw    $s0 -12($fp)
    lw    $s1 -16($fp)
    lw    $s2 -20($fp)
    lw    $ra -4($fp)
    lw    $fp -8($fp)
    addi  $sp $sp 20
    jr    $ra

    .text
    .globl  QS_Print_3
QS_Print_3:
    sw    $ra -4($sp)
    sw    $fp -8($sp)
    move  $fp $sp
    addi  $sp $sp -8
    move  $t3 $a0
    li    $t2 0
L41:lw    $t0 8($t3)
    slt   $t0 $t2 $t0
    bne   $t0 1   L44
    lw    $t1 4($t3)
    lw    $t0 ($t1)
    slt   $t0 $t2 $t0
    bne   $t0 1   L42
    slti  $t0 $t2 0
    bne   $t0 1   L43
L42:j     _error
L43:addi  $sp $sp -4
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
    b     L41
L44:li    $v0 0
    lw    $ra -4($fp)
    lw    $fp -8($fp)
    addi  $sp $sp 8
    jr    $ra

    .text
    .globl  QS_Init_4
QS_Init_4:
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
L45:slt   $t0 $t2 $t6
    bne   $t0 1   L46
    sw    $t1 ($t4)
    addi  $t4 $t4 4
    addi  $t2 $t2 4
    b     L45
L46:sw    $t7 ($t5)
    sw    $t5 4($t3)
    lw    $t2 4($t3)
    li    $t1 0
    lw    $t0 ($t2)
    slt   $t0 $t1 $t0
    bne   $t0 1   L47
    slti  $t0 $t1 0
    bne   $t0 1   L48
L47:j     _error
L48:addi  $sp $sp -4
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
    bne   $t0 1   L49
    slti  $t0 $t1 0
    bne   $t0 1   L50
L49:j     _error
L50:addi  $sp $sp -4
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
    bne   $t0 1   L51
    slti  $t0 $t1 0
    bne   $t0 1   L52
L51:j     _error
L52:addi  $sp $sp -4
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
    bne   $t0 1   L53
    slti  $t0 $t1 0
    bne   $t0 1   L54
L53:j     _error
L54:addi  $sp $sp -4
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
    bne   $t0 1   L55
    slti  $t0 $t1 0
    bne   $t0 1   L56
L55:j     _error
L56:addi  $sp $sp -4
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
    bne   $t0 1   L57
    slti  $t0 $t1 0
    bne   $t0 1   L58
L57:j     _error
L58:addi  $sp $sp -4
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
    bne   $t0 1   L59
    slti  $t0 $t1 0
    bne   $t0 1   L60
L59:j     _error
L60:addi  $sp $sp -4
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
    bne   $t0 1   L61
    slti  $t0 $t1 0
    bne   $t0 1   L62
L61:j     _error
L62:addi  $sp $sp -4
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
    bne   $t0 1   L63
    slti  $t0 $t1 0
    bne   $t0 1   L64
L63:j     _error
L64:addi  $sp $sp -4
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
    bne   $t0 1   L65
    slti  $t0 $t1 0
    bne   $t0 1   L66
L65:j     _error
L66:addi  $sp $sp -4
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

