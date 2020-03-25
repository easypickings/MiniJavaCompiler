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
    jal   A_run_1
    move  $t0 $v0
    move  $a0 $t0
    jal   _print
    lw    $ra -4($fp)
    lw    $fp -8($fp)
    addi  $sp $sp 8
    jr    $ra

    .text
    .globl  A_run_1
A_run_1:
    sw    $ra -4($sp)
    sw    $fp -8($sp)
    move  $fp $sp
    addi  $sp $sp -8
    li    $t6 20
    addi  $sp $sp -4
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
L3: slt   $t0 $t2 $t5
    bne   $t0 1   L4
    sw    $t1 ($t3)
    addi  $t3 $t3 4
    addi  $t2 $t2 4
    b     L3
L4: sw    $t6 ($t4)
    move  $t2 $t4
    li    $t1 10
    lw    $t0 ($t2)
    slt   $t0 $t1 $t0
    bne   $t0 1   L5
    slti  $t0 $t1 0
    bne   $t0 1   L6
L5: j     _error
L6: addi  $sp $sp -4
    sw    $v0 ($sp)
    li    $v0 4
    mul   $t0 $t1 $v0
    lw    $v0 ($sp)
    addi  $sp $sp 4
    add   $t0 $t2 $t0
    lw    $t0 4($t0)
    move  $a0 $t0
    jal   _print
    li    $t1 40
    lw    $t0 ($t2)
    slt   $t0 $t1 $t0
    bne   $t0 1   L7
    slti  $t0 $t1 0
    bne   $t0 1   L8
L7: j     _error
L8: addi  $sp $sp -4
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

