    .text
    .globl  main
main:
    sw    $ra -4($sp)
    sw    $fp -8($sp)
    move  $fp $sp
    addi  $sp $sp -8
    li    $t4 4
    move  $a0 $t4
    jal   _halloc
    move  $t6 $v0
    move  $t3 $t6
    li    $t2 0
    li    $t1 0
L1: slt   $t0 $t2 $t4
    bne   $t0 1   L2
    sw    $t1 ($t3)
    addi  $t3 $t3 4
    addi  $t2 $t2 4
    b     L1
L2: li    $t5 1
    li    $t4 2
    li    $t3 3
    li    $t2 4
    li    $t1 5
    li    $t0 6
    move  $a0 $t6
    move  $a1 $t5
    move  $a2 $t4
    move  $a3 $t3
    sw    $t2 -12($sp)
    sw    $t1 -16($sp)
    sw    $t0 -20($sp)
    jal   MT4_Start_1
    move  $t0 $v0
    move  $a0 $t0
    jal   _print
    lw    $ra -4($fp)
    lw    $fp -8($fp)
    addi  $sp $sp 8
    jr    $ra

    .text
    .globl  MT4_Start_1
MT4_Start_1:
    sw    $ra -4($sp)
    sw    $fp -8($sp)
    move  $fp $sp
    addi  $sp $sp -20
    move  $t6 $a0
    move  $t5 $a1
    move  $t4 $a2
    move  $t3 $a3
    lw    $t2 -12($fp)
    lw    $t1 -16($fp)
    lw    $t0 -20($fp)
    move  $a0 $t5
    jal   _print
    move  $a0 $t4
    jal   _print
    move  $a0 $t3
    jal   _print
    move  $a0 $t2
    jal   _print
    move  $a0 $t1
    jal   _print
    move  $a0 $t0
    jal   _print
    move  $a0 $t6
    move  $a1 $t0
    move  $a2 $t1
    move  $a3 $t2
    sw    $t3 -12($sp)
    sw    $t4 -16($sp)
    sw    $t5 -20($sp)
    jal   MT4_Change_2
    move  $t0 $v0
    move  $v0 $t0
    lw    $ra -4($fp)
    lw    $fp -8($fp)
    addi  $sp $sp 20
    jr    $ra

    .text
    .globl  MT4_Change_2
MT4_Change_2:
    sw    $ra -4($sp)
    sw    $fp -8($sp)
    move  $fp $sp
    addi  $sp $sp -20
    move  $t5 $a1
    move  $t4 $a2
    move  $t3 $a3
    lw    $t2 -12($fp)
    lw    $t1 -16($fp)
    lw    $t0 -20($fp)
    move  $a0 $t5
    jal   _print
    move  $a0 $t4
    jal   _print
    move  $a0 $t3
    jal   _print
    move  $a0 $t2
    jal   _print
    move  $a0 $t1
    jal   _print
    move  $a0 $t0
    jal   _print
    li    $v0 0
    lw    $ra -4($fp)
    lw    $fp -8($fp)
    addi  $sp $sp 20
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

