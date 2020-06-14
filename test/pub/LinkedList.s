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
    jal   LL_Start_17
    move  $t0 $v0
    move  $a0 $t0
    jal   _print
    lw    $ra -4($fp)
    lw    $fp -8($fp)
    addi  $sp $sp 8
    jr    $ra

    .text
    .globl  Element_Init_1
Element_Init_1:
    sw    $ra -4($sp)
    sw    $fp -8($sp)
    move  $fp $sp
    addi  $sp $sp -8
    move  $t3 $a0
    move  $t2 $a1
    move  $t1 $a2
    move  $t0 $a3
    sw    $t2 12($t3)
    sw    $t1 4($t3)
    sw    $t0 8($t3)
    li    $v0 1
    lw    $ra -4($fp)
    lw    $fp -8($fp)
    addi  $sp $sp 8
    jr    $ra

    .text
    .globl  Element_GetAge_2
Element_GetAge_2:
    sw    $ra -4($sp)
    sw    $fp -8($sp)
    move  $fp $sp
    addi  $sp $sp -8
    move  $t0 $a0
    lw    $t0 12($t0)
    move  $v0 $t0
    lw    $ra -4($fp)
    lw    $fp -8($fp)
    addi  $sp $sp 8
    jr    $ra

    .text
    .globl  Element_GetSalary_3
Element_GetSalary_3:
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
    .globl  Element_GetMarried_4
Element_GetMarried_4:
    sw    $ra -4($sp)
    sw    $fp -8($sp)
    move  $fp $sp
    addi  $sp $sp -8
    move  $t0 $a0
    lw    $t0 8($t0)
    move  $v0 $t0
    lw    $ra -4($fp)
    lw    $fp -8($fp)
    addi  $sp $sp 8
    jr    $ra

    .text
    .globl  Element_Equal_5
Element_Equal_5:
    sw    $ra -4($sp)
    sw    $fp -8($sp)
    move  $fp $sp
    addi  $sp $sp -20
    sw    $s0 -12($fp)
    sw    $s1 -16($fp)
    sw    $s2 -20($fp)
    move  $s2 $a0
    move  $s1 $a1
    li    $s0 1
    move  $a0 $s1
    jal   Element_GetAge_2
    move  $t0 $v0
    move  $t1 $t0
    lw    $t0 12($s2)
    move  $a0 $s2
    move  $a1 $t1
    move  $a2 $t0
    jal   Element_Compare_6
    move  $t0 $v0
    slti  $t0 $t0 1
    bne   $t0 1   L3
    li    $s0 0
    b     L12
L3: move  $a0 $s1
    jal   Element_GetSalary_3
    move  $t0 $v0
    move  $t1 $t0
    lw    $t0 4($s2)
    move  $a0 $s2
    move  $a1 $t1
    move  $a2 $t0
    jal   Element_Compare_6
    move  $t0 $v0
    slti  $t0 $t0 1
    bne   $t0 1   L4
    li    $s0 0
    b     L12
L4: lw    $t0 8($s2)
    bne   $t0 1   L7
    move  $a0 $s1
    jal   Element_GetMarried_4
    move  $t0 $v0
    slti  $t0 $t0 1
    bne   $t0 1   L12
    li    $s0 0
    b     L12
    b     L12
L7: move  $a0 $s1
    jal   Element_GetMarried_4
    move  $t0 $v0
    bne   $t0 1   L12
    li    $s0 0
    b     L12
L12:move  $v0 $s0
    lw    $s0 -12($fp)
    lw    $s1 -16($fp)
    lw    $s2 -20($fp)
    lw    $ra -4($fp)
    lw    $fp -8($fp)
    addi  $sp $sp 20
    jr    $ra

    .text
    .globl  Element_Compare_6
Element_Compare_6:
    sw    $ra -4($sp)
    sw    $fp -8($sp)
    move  $fp $sp
    addi  $sp $sp -8
    move  $t3 $a1
    move  $t2 $a2
    addi  $t0 $t2 1
    move  $t1 $t0
    slt   $t0 $t3 $t2
    bne   $t0 1   L13
    li    $t0 0
    b     L16
L13:slt   $t0 $t3 $t1
    slti  $t0 $t0 1
    bne   $t0 1   L14
    li    $t0 0
    b     L16
L14:li    $t0 1
L16:move  $v0 $t0
    lw    $ra -4($fp)
    lw    $fp -8($fp)
    addi  $sp $sp 8
    jr    $ra

    .text
    .globl  List_Init_7
List_Init_7:
    sw    $ra -4($sp)
    sw    $fp -8($sp)
    move  $fp $sp
    addi  $sp $sp -8
    move  $t1 $a0
    li    $t0 1
    sw    $t0 12($t1)
    li    $v0 1
    lw    $ra -4($fp)
    lw    $fp -8($fp)
    addi  $sp $sp 8
    jr    $ra

    .text
    .globl  List_InitNew_8
List_InitNew_8:
    sw    $ra -4($sp)
    sw    $fp -8($sp)
    move  $fp $sp
    addi  $sp $sp -8
    move  $t3 $a0
    move  $t2 $a1
    move  $t1 $a2
    move  $t0 $a3
    sw    $t0 12($t3)
    sw    $t2 4($t3)
    sw    $t1 8($t3)
    li    $v0 1
    lw    $ra -4($fp)
    lw    $fp -8($fp)
    addi  $sp $sp 8
    jr    $ra

    .text
    .globl  List_Insert_9
List_Insert_9:
    sw    $ra -4($sp)
    sw    $fp -8($sp)
    move  $fp $sp
    addi  $sp $sp -12
    sw    $s0 -12($fp)
    move  $t0 $a0
    move  $t7 $a1
    move  $t6 $t0
    li    $t4 16
    move  $a0 $t4
    jal   _halloc
    move  $t5 $v0
    move  $t3 $t5
    li    $t2 0
    li    $t1 0
L17:slt   $t0 $t2 $t4
    bne   $t0 1   L18
    sw    $t1 ($t3)
    addi  $t3 $t3 4
    addi  $t2 $t2 4
    b     L17
L18:move  $s0 $t5
    li    $t0 0
    move  $a0 $s0
    move  $a1 $t7
    move  $a2 $t6
    move  $a3 $t0
    jal   List_InitNew_8
    move  $v0 $s0
    lw    $s0 -12($fp)
    lw    $ra -4($fp)
    lw    $fp -8($fp)
    addi  $sp $sp 12
    jr    $ra

    .text
    .globl  List_SetNext_10
List_SetNext_10:
    sw    $ra -4($sp)
    sw    $fp -8($sp)
    move  $fp $sp
    addi  $sp $sp -8
    move  $t1 $a0
    move  $t0 $a1
    sw    $t0 8($t1)
    li    $v0 1
    lw    $ra -4($fp)
    lw    $fp -8($fp)
    addi  $sp $sp 8
    jr    $ra

    .text
    .globl  List_Delete_11
List_Delete_11:
    sw    $ra -4($sp)
    sw    $fp -8($sp)
    move  $fp $sp
    addi  $sp $sp -40
    sw    $s0 -12($fp)
    sw    $s1 -16($fp)
    sw    $s2 -20($fp)
    sw    $s3 -24($fp)
    sw    $s4 -28($fp)
    sw    $s5 -32($fp)
    sw    $s6 -36($fp)
    sw    $s7 -40($fp)
    move  $t1 $a0
    move  $s7 $a1
    move  $s6 $t1
    li    $s5 0
    li    $t0 0
    addi  $t0 $t0 -1
    move  $s0 $t0
    move  $s4 $t1
    move  $s3 $t1
    lw    $t0 12($t1)
    move  $s2 $t0
    lw    $t0 4($t1)
    move  $s1 $t0
L19:slti  $t0 $s2 1
    li    $t1 0
    bne   $t0 1   L20
    slti  $t0 $s5 1
    slt   $t1 $t1 $t0
L20:bne   $t1 1   L27
    move  $a0 $s7
    move  $a1 $s1
    jal   Element_Equal_5
    move  $t0 $v0
    bne   $t0 1   L24
    li    $s5 1
    slti  $t0 $s0 0
    bne   $t0 1   L21
    move  $a0 $s4
    jal   List_GetNext_15
    move  $t0 $v0
    move  $s6 $t0
    b     L24
L21:li    $t0 0
    addi  $t0 $t0 -555
    move  $a0 $t0
    jal   _print
    move  $a0 $s4
    jal   List_GetNext_15
    move  $t0 $v0
    move  $a0 $s3
    move  $a1 $t0
    jal   List_SetNext_10
    li    $t0 0
    addi  $t0 $t0 -555
    move  $a0 $t0
    jal   _print
    b     L24
L24:slti  $t0 $s5 1
    bne   $t0 1   L19
    move  $s3 $s4
    move  $a0 $s4
    jal   List_GetNext_15
    move  $t0 $v0
    move  $s4 $t0
    move  $a0 $s4
    jal   List_GetEnd_13
    move  $t0 $v0
    move  $s2 $t0
    move  $a0 $s4
    jal   List_GetElem_14
    move  $t0 $v0
    move  $s1 $t0
    li    $s0 1
    b     L19
    b     L19
L27:move  $v0 $s6
    lw    $s0 -12($fp)
    lw    $s1 -16($fp)
    lw    $s2 -20($fp)
    lw    $s3 -24($fp)
    lw    $s4 -28($fp)
    lw    $s5 -32($fp)
    lw    $s6 -36($fp)
    lw    $s7 -40($fp)
    lw    $ra -4($fp)
    lw    $fp -8($fp)
    addi  $sp $sp 40
    jr    $ra

    .text
    .globl  List_Search_12
List_Search_12:
    sw    $ra -4($sp)
    sw    $fp -8($sp)
    move  $fp $sp
    addi  $sp $sp -24
    sw    $s0 -12($fp)
    sw    $s1 -16($fp)
    sw    $s2 -20($fp)
    sw    $s3 -24($fp)
    move  $t1 $a0
    move  $s3 $a1
    li    $s2 0
    move  $s1 $t1
    lw    $t0 12($t1)
    move  $s0 $t0
    lw    $t0 4($t1)
    move  $t1 $t0
L28:slti  $t0 $s0 1
    bne   $t0 1   L31
    move  $a0 $s3
    move  $a1 $t1
    jal   Element_Equal_5
    move  $t0 $v0
    bne   $t0 1   L30
    li    $s2 1
    b     L30
L30:move  $a0 $s1
    jal   List_GetNext_15
    move  $t0 $v0
    move  $s1 $t0
    move  $a0 $s1
    jal   List_GetEnd_13
    move  $t0 $v0
    move  $s0 $t0
    move  $a0 $s1
    jal   List_GetElem_14
    move  $t0 $v0
    move  $t1 $t0
    b     L28
L31:move  $v0 $s2
    lw    $s0 -12($fp)
    lw    $s1 -16($fp)
    lw    $s2 -20($fp)
    lw    $s3 -24($fp)
    lw    $ra -4($fp)
    lw    $fp -8($fp)
    addi  $sp $sp 24
    jr    $ra

    .text
    .globl  List_GetEnd_13
List_GetEnd_13:
    sw    $ra -4($sp)
    sw    $fp -8($sp)
    move  $fp $sp
    addi  $sp $sp -8
    move  $t0 $a0
    lw    $t0 12($t0)
    move  $v0 $t0
    lw    $ra -4($fp)
    lw    $fp -8($fp)
    addi  $sp $sp 8
    jr    $ra

    .text
    .globl  List_GetElem_14
List_GetElem_14:
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
    .globl  List_GetNext_15
List_GetNext_15:
    sw    $ra -4($sp)
    sw    $fp -8($sp)
    move  $fp $sp
    addi  $sp $sp -8
    move  $t0 $a0
    lw    $t0 8($t0)
    move  $v0 $t0
    lw    $ra -4($fp)
    lw    $fp -8($fp)
    addi  $sp $sp 8
    jr    $ra

    .text
    .globl  List_Print_16
List_Print_16:
    sw    $ra -4($sp)
    sw    $fp -8($sp)
    move  $fp $sp
    addi  $sp $sp -16
    sw    $s0 -12($fp)
    sw    $s1 -16($fp)
    move  $t1 $a0
    move  $s1 $t1
    lw    $t0 12($t1)
    move  $s0 $t0
    lw    $t0 4($t1)
    move  $t1 $t0
L32:slti  $t0 $s0 1
    bne   $t0 1   L33
    move  $a0 $t1
    jal   Element_GetAge_2
    move  $t0 $v0
    move  $a0 $t0
    jal   _print
    move  $a0 $s1
    jal   List_GetNext_15
    move  $t0 $v0
    move  $s1 $t0
    move  $a0 $s1
    jal   List_GetEnd_13
    move  $t0 $v0
    move  $s0 $t0
    move  $a0 $s1
    jal   List_GetElem_14
    move  $t0 $v0
    move  $t1 $t0
    b     L32
L33:li    $v0 1
    lw    $s0 -12($fp)
    lw    $s1 -16($fp)
    lw    $ra -4($fp)
    lw    $fp -8($fp)
    addi  $sp $sp 16
    jr    $ra

    .text
    .globl  LL_Start_17
LL_Start_17:
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
    move  $t5 $v0
    move  $t3 $t5
    li    $t2 0
    li    $t1 0
L34:slt   $t0 $t2 $t4
    bne   $t0 1   L35
    sw    $t1 ($t3)
    addi  $t3 $t3 4
    addi  $t2 $t2 4
    b     L34
L35:move  $s0 $t5
    move  $a0 $s0
    jal   List_Init_7
    move  $s2 $s0
    move  $a0 $s2
    jal   List_Init_7
    move  $a0 $s2
    jal   List_Print_16
    li    $t4 16
    move  $a0 $t4
    jal   _halloc
    move  $t5 $v0
    move  $t3 $t5
    li    $t2 0
    li    $t1 0
L36:slt   $t0 $t2 $t4
    bne   $t0 1   L37
    sw    $t1 ($t3)
    addi  $t3 $t3 4
    addi  $t2 $t2 4
    b     L36
L37:move  $s0 $t5
    li    $t2 25
    li    $t1 37000
    li    $t0 0
    move  $a0 $s0
    move  $a1 $t2
    move  $a2 $t1
    move  $a3 $t0
    jal   Element_Init_1
    move  $a0 $s2
    move  $a1 $s0
    jal   List_Insert_9
    move  $t0 $v0
    move  $s2 $t0
    move  $a0 $s2
    jal   List_Print_16
    li    $a0 10000000
    jal   _print
    li    $t4 16
    move  $a0 $t4
    jal   _halloc
    move  $t5 $v0
    move  $t3 $t5
    li    $t2 0
    li    $t1 0
L38:slt   $t0 $t2 $t4
    bne   $t0 1   L39
    sw    $t1 ($t3)
    addi  $t3 $t3 4
    addi  $t2 $t2 4
    b     L38
L39:move  $s0 $t5
    li    $t2 39
    li    $t1 42000
    li    $t0 1
    move  $a0 $s0
    move  $a1 $t2
    move  $a2 $t1
    move  $a3 $t0
    jal   Element_Init_1
    move  $s1 $s0
    move  $a0 $s2
    move  $a1 $s0
    jal   List_Insert_9
    move  $t0 $v0
    move  $s2 $t0
    move  $a0 $s2
    jal   List_Print_16
    li    $a0 10000000
    jal   _print
    li    $t4 16
    move  $a0 $t4
    jal   _halloc
    move  $t5 $v0
    move  $t3 $t5
    li    $t2 0
    li    $t1 0
L40:slt   $t0 $t2 $t4
    bne   $t0 1   L41
    sw    $t1 ($t3)
    addi  $t3 $t3 4
    addi  $t2 $t2 4
    b     L40
L41:move  $s0 $t5
    li    $t2 22
    li    $t1 34000
    li    $t0 0
    move  $a0 $s0
    move  $a1 $t2
    move  $a2 $t1
    move  $a3 $t0
    jal   Element_Init_1
    move  $a0 $s2
    move  $a1 $s0
    jal   List_Insert_9
    move  $t0 $v0
    move  $s2 $t0
    move  $a0 $s2
    jal   List_Print_16
    li    $t4 16
    move  $a0 $t4
    jal   _halloc
    move  $t5 $v0
    move  $t3 $t5
    li    $t2 0
    li    $t1 0
L42:slt   $t0 $t2 $t4
    bne   $t0 1   L43
    sw    $t1 ($t3)
    addi  $t3 $t3 4
    addi  $t2 $t2 4
    b     L42
L43:move  $s0 $t5
    li    $t2 27
    li    $t1 34000
    li    $t0 0
    move  $a0 $s0
    move  $a1 $t2
    move  $a2 $t1
    move  $a3 $t0
    jal   Element_Init_1
    move  $a0 $s2
    move  $a1 $s1
    jal   List_Search_12
    move  $t0 $v0
    move  $a0 $t0
    jal   _print
    move  $a0 $s2
    move  $a1 $s0
    jal   List_Search_12
    move  $t0 $v0
    move  $a0 $t0
    jal   _print
    li    $a0 10000000
    jal   _print
    li    $t4 16
    move  $a0 $t4
    jal   _halloc
    move  $t5 $v0
    move  $t3 $t5
    li    $t2 0
    li    $t1 0
L44:slt   $t0 $t2 $t4
    bne   $t0 1   L45
    sw    $t1 ($t3)
    addi  $t3 $t3 4
    addi  $t2 $t2 4
    b     L44
L45:move  $s0 $t5
    li    $t2 28
    li    $t1 35000
    li    $t0 0
    move  $a0 $s0
    move  $a1 $t2
    move  $a2 $t1
    move  $a3 $t0
    jal   Element_Init_1
    move  $a0 $s2
    move  $a1 $s0
    jal   List_Insert_9
    move  $t0 $v0
    move  $s2 $t0
    move  $a0 $s2
    jal   List_Print_16
    li    $a0 2220000
    jal   _print
    move  $a0 $s2
    move  $a1 $s1
    jal   List_Delete_11
    move  $t0 $v0
    move  $s2 $t0
    move  $a0 $s2
    jal   List_Print_16
    li    $a0 33300000
    jal   _print
    move  $a0 $s2
    move  $a1 $s0
    jal   List_Delete_11
    move  $t0 $v0
    move  $s2 $t0
    move  $a0 $s2
    jal   List_Print_16
    li    $a0 44440000
    jal   _print
    li    $v0 0
    lw    $s0 -12($fp)
    lw    $s1 -16($fp)
    lw    $s2 -20($fp)
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

