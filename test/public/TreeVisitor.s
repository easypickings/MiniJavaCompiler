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
    jal   TV_Start_1
    move  $t0 $v0
    move  $a0 $t0
    jal   _print
    lw    $ra -4($fp)
    lw    $fp -8($fp)
    addi  $sp $sp 8
    jr    $ra

    .text
    .globl  TV_Start_1
TV_Start_1:
    sw    $ra -4($sp)
    sw    $fp -8($sp)
    move  $fp $sp
    addi  $sp $sp -16
    sw    $s0 -12($fp)
    sw    $s1 -16($fp)
    li    $t4 28
    move  $a0 $t4
    jal   _halloc
    move  $t5 $v0
    move  $t3 $t5
    li    $t2 0
    li    $t1 0
L3: slt   $t0 $t2 $t4
    bne   $t0 1   L4
    sw    $t1 ($t3)
    addi  $t3 $t3 4
    addi  $t2 $t2 4
    b     L3
L4: move  $s0 $t5
    li    $t0 16
    move  $a0 $s0
    move  $a1 $t0
    jal   Tree_Init_2
    move  $a0 $s0
    jal   Tree_Print_20
    li    $a0 100000000
    jal   _print
    li    $t0 8
    move  $a0 $s0
    move  $a1 $t0
    jal   Tree_Insert_14
    li    $t0 24
    move  $a0 $s0
    move  $a1 $t0
    jal   Tree_Insert_14
    li    $t0 4
    move  $a0 $s0
    move  $a1 $t0
    jal   Tree_Insert_14
    li    $t0 12
    move  $a0 $s0
    move  $a1 $t0
    jal   Tree_Insert_14
    li    $t0 20
    move  $a0 $s0
    move  $a1 $t0
    jal   Tree_Insert_14
    li    $t0 28
    move  $a0 $s0
    move  $a1 $t0
    jal   Tree_Insert_14
    li    $t0 14
    move  $a0 $s0
    move  $a1 $t0
    jal   Tree_Insert_14
    move  $a0 $s0
    jal   Tree_Print_20
    li    $a0 100000000
    jal   _print
    li    $t4 12
    move  $a0 $t4
    jal   _halloc
    move  $s1 $v0
    move  $t3 $s1
    li    $t2 0
    li    $t1 0
L5: slt   $t0 $t2 $t4
    bne   $t0 1   L6
    sw    $t1 ($t3)
    addi  $t3 $t3 4
    addi  $t2 $t2 4
    b     L5
L6: jal   MyVisitor
    move  $t0 $v0
    sw    $t0 ($s1)
    move  $t0 $s1
    li    $a0 50000000
    jal   _print
    move  $a0 $s0
    move  $a1 $t0
    jal   Tree_accept_22
    li    $a0 100000000
    jal   _print
    li    $t0 24
    move  $a0 $s0
    move  $a1 $t0
    jal   Tree_Search_19
    move  $t0 $v0
    move  $a0 $t0
    jal   _print
    li    $t0 12
    move  $a0 $s0
    move  $a1 $t0
    jal   Tree_Search_19
    move  $t0 $v0
    move  $a0 $t0
    jal   _print
    li    $t0 16
    move  $a0 $s0
    move  $a1 $t0
    jal   Tree_Search_19
    move  $t0 $v0
    move  $a0 $t0
    jal   _print
    li    $t0 50
    move  $a0 $s0
    move  $a1 $t0
    jal   Tree_Search_19
    move  $t0 $v0
    move  $a0 $t0
    jal   _print
    li    $t0 12
    move  $a0 $s0
    move  $a1 $t0
    jal   Tree_Search_19
    move  $t0 $v0
    move  $a0 $t0
    jal   _print
    li    $t0 12
    move  $a0 $s0
    move  $a1 $t0
    jal   Tree_Delete_15
    move  $a0 $s0
    jal   Tree_Print_20
    li    $t0 12
    move  $a0 $s0
    move  $a1 $t0
    jal   Tree_Search_19
    move  $t0 $v0
    move  $a0 $t0
    jal   _print
    li    $v0 0
    lw    $s0 -12($fp)
    lw    $s1 -16($fp)
    lw    $ra -4($fp)
    lw    $fp -8($fp)
    addi  $sp $sp 16
    jr    $ra

    .text
    .globl  Tree_Init_2
Tree_Init_2:
    sw    $ra -4($sp)
    sw    $fp -8($sp)
    move  $fp $sp
    addi  $sp $sp -8
    move  $t1 $a0
    move  $t0 $a1
    sw    $t0 24($t1)
    li    $t0 0
    sw    $t0 20($t1)
    li    $t0 0
    sw    $t0 8($t1)
    li    $v0 1
    lw    $ra -4($fp)
    lw    $fp -8($fp)
    addi  $sp $sp 8
    jr    $ra

    .text
    .globl  Tree_SetRight_3
Tree_SetRight_3:
    sw    $ra -4($sp)
    sw    $fp -8($sp)
    move  $fp $sp
    addi  $sp $sp -8
    move  $t1 $a0
    move  $t0 $a1
    sw    $t0 16($t1)
    li    $v0 1
    lw    $ra -4($fp)
    lw    $fp -8($fp)
    addi  $sp $sp 8
    jr    $ra

    .text
    .globl  Tree_SetLeft_4
Tree_SetLeft_4:
    sw    $ra -4($sp)
    sw    $fp -8($sp)
    move  $fp $sp
    addi  $sp $sp -8
    move  $t1 $a0
    move  $t0 $a1
    sw    $t0 4($t1)
    li    $v0 1
    lw    $ra -4($fp)
    lw    $fp -8($fp)
    addi  $sp $sp 8
    jr    $ra

    .text
    .globl  Tree_GetRight_5
Tree_GetRight_5:
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
    .globl  Tree_GetLeft_6
Tree_GetLeft_6:
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
    .globl  Tree_GetKey_7
Tree_GetKey_7:
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
    .globl  Tree_SetKey_8
Tree_SetKey_8:
    sw    $ra -4($sp)
    sw    $fp -8($sp)
    move  $fp $sp
    addi  $sp $sp -8
    move  $t1 $a0
    move  $t0 $a1
    sw    $t0 24($t1)
    li    $v0 1
    lw    $ra -4($fp)
    lw    $fp -8($fp)
    addi  $sp $sp 8
    jr    $ra

    .text
    .globl  Tree_GetHas_Right_9
Tree_GetHas_Right_9:
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
    .globl  Tree_GetHas_Left_10
Tree_GetHas_Left_10:
    sw    $ra -4($sp)
    sw    $fp -8($sp)
    move  $fp $sp
    addi  $sp $sp -8
    move  $t0 $a0
    lw    $t0 20($t0)
    move  $v0 $t0
    lw    $ra -4($fp)
    lw    $fp -8($fp)
    addi  $sp $sp 8
    jr    $ra

    .text
    .globl  Tree_SetHas_Left_11
Tree_SetHas_Left_11:
    sw    $ra -4($sp)
    sw    $fp -8($sp)
    move  $fp $sp
    addi  $sp $sp -8
    move  $t1 $a0
    move  $t0 $a1
    sw    $t0 20($t1)
    li    $v0 1
    lw    $ra -4($fp)
    lw    $fp -8($fp)
    addi  $sp $sp 8
    jr    $ra

    .text
    .globl  Tree_SetHas_Right_12
Tree_SetHas_Right_12:
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
    .globl  Tree_Compare_13
Tree_Compare_13:
    sw    $ra -4($sp)
    sw    $fp -8($sp)
    move  $fp $sp
    addi  $sp $sp -8
    move  $t3 $a1
    move  $t2 $a2
    addi  $t0 $t2 1
    move  $t1 $t0
    slt   $t0 $t3 $t2
    bne   $t0 1   L7
    li    $t0 0
    b     L10
L7: slt   $t0 $t3 $t1
    slti  $t0 $t0 1
    bne   $t0 1   L8
    li    $t0 0
    b     L10
L8: li    $t0 1
L10:move  $v0 $t0
    lw    $ra -4($fp)
    lw    $fp -8($fp)
    addi  $sp $sp 8
    jr    $ra

    .text
    .globl  Tree_Insert_14
Tree_Insert_14:
    sw    $ra -4($sp)
    sw    $fp -8($sp)
    move  $fp $sp
    addi  $sp $sp -24
    sw    $s0 -12($fp)
    sw    $s1 -16($fp)
    sw    $s2 -20($fp)
    sw    $s3 -24($fp)
    move  $s0 $a0
    move  $s3 $a1
    li    $t4 28
    move  $a0 $t4
    jal   _halloc
    move  $t5 $v0
    move  $t3 $t5
    li    $t2 0
    li    $t1 0
L11:slt   $t0 $t2 $t4
    bne   $t0 1   L12
    sw    $t1 ($t3)
    addi  $t3 $t3 4
    addi  $t2 $t2 4
    b     L11
L12:move  $s2 $t5
    move  $a0 $s2
    move  $a1 $s3
    jal   Tree_Init_2
    move  $s1 $s0
    li    $s0 1
L13:bne   $s0 1   L20
    move  $a0 $s1
    jal   Tree_GetKey_7
    move  $t0 $v0
    slt   $t0 $s3 $t0
    bne   $t0 1   L16
    move  $a0 $s1
    jal   Tree_GetHas_Left_10
    move  $t0 $v0
    bne   $t0 1   L14
    move  $a0 $s1
    jal   Tree_GetLeft_6
    move  $t0 $v0
    move  $s1 $t0
    b     L13
L14:li    $s0 0
    li    $t0 1
    move  $a0 $s1
    move  $a1 $t0
    jal   Tree_SetHas_Left_11
    move  $a0 $s1
    move  $a1 $s2
    jal   Tree_SetLeft_4
    b     L13
L16:move  $a0 $s1
    jal   Tree_GetHas_Right_9
    move  $t0 $v0
    bne   $t0 1   L17
    move  $a0 $s1
    jal   Tree_GetRight_5
    move  $t0 $v0
    move  $s1 $t0
    b     L13
L17:li    $s0 0
    li    $t0 1
    move  $a0 $s1
    move  $a1 $t0
    jal   Tree_SetHas_Right_12
    move  $a0 $s1
    move  $a1 $s2
    jal   Tree_SetRight_3
    b     L13
L20:li    $v0 1
    lw    $s0 -12($fp)
    lw    $s1 -16($fp)
    lw    $s2 -20($fp)
    lw    $s3 -24($fp)
    lw    $ra -4($fp)
    lw    $fp -8($fp)
    addi  $sp $sp 24
    jr    $ra

    .text
    .globl  Tree_Delete_15
Tree_Delete_15:
    sw    $ra -4($sp)
    sw    $fp -8($sp)
    move  $fp $sp
    addi  $sp $sp -36
    sw    $s0 -12($fp)
    sw    $s1 -16($fp)
    sw    $s2 -20($fp)
    sw    $s3 -24($fp)
    sw    $s4 -28($fp)
    sw    $s5 -32($fp)
    sw    $s6 -36($fp)
    move  $s6 $a0
    move  $s5 $a1
    move  $s4 $s6
    move  $s3 $s6
    li    $s2 1
    li    $s1 0
    li    $s0 1
L21:bne   $s2 1   L35
    move  $a0 $s4
    jal   Tree_GetKey_7
    move  $t0 $v0
    move  $t1 $t0
    slt   $t0 $s5 $t1
    bne   $t0 1   L24
    move  $a0 $s4
    jal   Tree_GetHas_Left_10
    move  $t0 $v0
    bne   $t0 1   L22
    move  $s3 $s4
    move  $a0 $s4
    jal   Tree_GetLeft_6
    move  $t0 $v0
    move  $s4 $t0
    b     L34
L22:li    $s2 0
    b     L34
L24:slt   $t0 $t1 $s5
    bne   $t0 1   L27
    move  $a0 $s4
    jal   Tree_GetHas_Right_9
    move  $t0 $v0
    bne   $t0 1   L25
    move  $s3 $s4
    move  $a0 $s4
    jal   Tree_GetRight_5
    move  $t0 $v0
    move  $s4 $t0
    b     L34
L25:li    $s2 0
    b     L34
L27:bne   $s0 1   L31
    move  $a0 $s4
    jal   Tree_GetHas_Right_9
    move  $t0 $v0
    slti  $t0 $t0 1
    li    $s0 0
    bne   $t0 1   L28
    move  $a0 $s4
    jal   Tree_GetHas_Left_10
    move  $t0 $v0
    slti  $t0 $t0 1
    slt   $s0 $s0 $t0
L28:bne   $s0 1   L29
    b     L32
L29:move  $a0 $s6
    move  $a1 $s3
    move  $a2 $s4
    jal   Tree_Remove_16
    b     L32
L31:move  $a0 $s6
    move  $a1 $s3
    move  $a2 $s4
    jal   Tree_Remove_16
L32:li    $s1 1
    li    $s2 0
L34:li    $s0 0
    b     L21
L35:move  $v0 $s1
    lw    $s0 -12($fp)
    lw    $s1 -16($fp)
    lw    $s2 -20($fp)
    lw    $s3 -24($fp)
    lw    $s4 -28($fp)
    lw    $s5 -32($fp)
    lw    $s6 -36($fp)
    lw    $ra -4($fp)
    lw    $fp -8($fp)
    addi  $sp $sp 36
    jr    $ra

    .text
    .globl  Tree_Remove_16
Tree_Remove_16:
    sw    $ra -4($sp)
    sw    $fp -8($sp)
    move  $fp $sp
    addi  $sp $sp -20
    sw    $s0 -12($fp)
    sw    $s1 -16($fp)
    sw    $s2 -20($fp)
    move  $s2 $a0
    move  $s1 $a1
    move  $s0 $a2
    move  $a0 $s0
    jal   Tree_GetHas_Left_10
    move  $t0 $v0
    bne   $t0 1   L36
    move  $a0 $s2
    move  $a1 $s1
    move  $a2 $s0
    jal   Tree_RemoveLeft_18
    b     L41
L36:move  $a0 $s0
    jal   Tree_GetHas_Right_9
    move  $t0 $v0
    bne   $t0 1   L37
    move  $a0 $s2
    move  $a1 $s1
    move  $a2 $s0
    jal   Tree_RemoveRight_17
    b     L41
L37:move  $a0 $s0
    jal   Tree_GetKey_7
    move  $t0 $v0
    move  $s0 $t0
    move  $a0 $s1
    jal   Tree_GetLeft_6
    move  $t0 $v0
    move  $a0 $t0
    jal   Tree_GetKey_7
    move  $t0 $v0
    move  $a0 $s2
    move  $a1 $s0
    move  $a2 $t0
    jal   Tree_Compare_13
    move  $t0 $v0
    bne   $t0 1   L38
    lw    $t0 12($s2)
    move  $a0 $s1
    move  $a1 $t0
    jal   Tree_SetLeft_4
    li    $t0 0
    move  $a0 $s1
    move  $a1 $t0
    jal   Tree_SetHas_Left_11
    b     L41
L38:lw    $t0 12($s2)
    move  $a0 $s1
    move  $a1 $t0
    jal   Tree_SetRight_3
    li    $t0 0
    move  $a0 $s1
    move  $a1 $t0
    jal   Tree_SetHas_Right_12
L41:li    $v0 1
    lw    $s0 -12($fp)
    lw    $s1 -16($fp)
    lw    $s2 -20($fp)
    lw    $ra -4($fp)
    lw    $fp -8($fp)
    addi  $sp $sp 20
    jr    $ra

    .text
    .globl  Tree_RemoveRight_17
Tree_RemoveRight_17:
    sw    $ra -4($sp)
    sw    $fp -8($sp)
    move  $fp $sp
    addi  $sp $sp -20
    sw    $s0 -12($fp)
    sw    $s1 -16($fp)
    sw    $s2 -20($fp)
    move  $s2 $a0
    move  $s1 $a1
    move  $s0 $a2
L42:move  $a0 $s0
    jal   Tree_GetHas_Right_9
    move  $t0 $v0
    bne   $t0 1   L43
    move  $a0 $s0
    jal   Tree_GetRight_5
    move  $t0 $v0
    move  $a0 $t0
    jal   Tree_GetKey_7
    move  $t0 $v0
    move  $a0 $s0
    move  $a1 $t0
    jal   Tree_SetKey_8
    move  $s1 $s0
    move  $a0 $s0
    jal   Tree_GetRight_5
    move  $t0 $v0
    move  $s0 $t0
    b     L42
L43:lw    $t0 12($s2)
    move  $a0 $s1
    move  $a1 $t0
    jal   Tree_SetRight_3
    li    $t0 0
    move  $a0 $s1
    move  $a1 $t0
    jal   Tree_SetHas_Right_12
    li    $v0 1
    lw    $s0 -12($fp)
    lw    $s1 -16($fp)
    lw    $s2 -20($fp)
    lw    $ra -4($fp)
    lw    $fp -8($fp)
    addi  $sp $sp 20
    jr    $ra

    .text
    .globl  Tree_RemoveLeft_18
Tree_RemoveLeft_18:
    sw    $ra -4($sp)
    sw    $fp -8($sp)
    move  $fp $sp
    addi  $sp $sp -20
    sw    $s0 -12($fp)
    sw    $s1 -16($fp)
    sw    $s2 -20($fp)
    move  $s2 $a0
    move  $s1 $a1
    move  $s0 $a2
L44:move  $a0 $s0
    jal   Tree_GetHas_Left_10
    move  $t0 $v0
    bne   $t0 1   L45
    move  $a0 $s0
    jal   Tree_GetLeft_6
    move  $t0 $v0
    move  $a0 $t0
    jal   Tree_GetKey_7
    move  $t0 $v0
    move  $a0 $s0
    move  $a1 $t0
    jal   Tree_SetKey_8
    move  $s1 $s0
    move  $a0 $s0
    jal   Tree_GetLeft_6
    move  $t0 $v0
    move  $s0 $t0
    b     L44
L45:lw    $t0 12($s2)
    move  $a0 $s1
    move  $a1 $t0
    jal   Tree_SetLeft_4
    li    $t0 0
    move  $a0 $s1
    move  $a1 $t0
    jal   Tree_SetHas_Left_11
    li    $v0 1
    lw    $s0 -12($fp)
    lw    $s1 -16($fp)
    lw    $s2 -20($fp)
    lw    $ra -4($fp)
    lw    $fp -8($fp)
    addi  $sp $sp 20
    jr    $ra

    .text
    .globl  Tree_Search_19
Tree_Search_19:
    sw    $ra -4($sp)
    sw    $fp -8($sp)
    move  $fp $sp
    addi  $sp $sp -24
    sw    $s0 -12($fp)
    sw    $s1 -16($fp)
    sw    $s2 -20($fp)
    sw    $s3 -24($fp)
    move  $t0 $a0
    move  $s3 $a1
    move  $s2 $t0
    li    $s0 1
    li    $s1 0
L46:bne   $s0 1   L55
    move  $a0 $s2
    jal   Tree_GetKey_7
    move  $t0 $v0
    move  $t1 $t0
    slt   $t0 $s3 $t1
    bne   $t0 1   L49
    move  $a0 $s2
    jal   Tree_GetHas_Left_10
    move  $t0 $v0
    bne   $t0 1   L47
    move  $a0 $s2
    jal   Tree_GetLeft_6
    move  $t0 $v0
    move  $s2 $t0
    b     L46
L47:li    $s0 0
    b     L46
L49:slt   $t0 $t1 $s3
    bne   $t0 1   L52
    move  $a0 $s2
    jal   Tree_GetHas_Right_9
    move  $t0 $v0
    bne   $t0 1   L50
    move  $a0 $s2
    jal   Tree_GetRight_5
    move  $t0 $v0
    move  $s2 $t0
    b     L46
L50:li    $s0 0
    b     L46
L52:li    $s1 1
    li    $s0 0
    b     L46
L55:move  $v0 $s1
    lw    $s0 -12($fp)
    lw    $s1 -16($fp)
    lw    $s2 -20($fp)
    lw    $s3 -24($fp)
    lw    $ra -4($fp)
    lw    $fp -8($fp)
    addi  $sp $sp 24
    jr    $ra

    .text
    .globl  Tree_Print_20
Tree_Print_20:
    sw    $ra -4($sp)
    sw    $fp -8($sp)
    move  $fp $sp
    addi  $sp $sp -8
    move  $t1 $a0
    move  $t0 $t1
    move  $a0 $t1
    move  $a1 $t0
    jal   Tree_RecPrint_21
    li    $v0 1
    lw    $ra -4($fp)
    lw    $fp -8($fp)
    addi  $sp $sp 8
    jr    $ra

    .text
    .globl  Tree_RecPrint_21
Tree_RecPrint_21:
    sw    $ra -4($sp)
    sw    $fp -8($sp)
    move  $fp $sp
    addi  $sp $sp -16
    sw    $s0 -12($fp)
    sw    $s1 -16($fp)
    move  $s1 $a0
    move  $s0 $a1
    move  $a0 $s0
    jal   Tree_GetHas_Left_10
    move  $t0 $v0
    bne   $t0 1   L57
    move  $a0 $s0
    jal   Tree_GetLeft_6
    move  $t0 $v0
    move  $a0 $s1
    move  $a1 $t0
    jal   Tree_RecPrint_21
    b     L57
L57:move  $a0 $s0
    jal   Tree_GetKey_7
    move  $t0 $v0
    move  $a0 $t0
    jal   _print
    move  $a0 $s0
    jal   Tree_GetHas_Right_9
    move  $t0 $v0
    bne   $t0 1   L59
    move  $a0 $s0
    jal   Tree_GetRight_5
    move  $t0 $v0
    move  $a0 $s1
    move  $a1 $t0
    jal   Tree_RecPrint_21
    b     L59
L59:li    $v0 1
    lw    $s0 -12($fp)
    lw    $s1 -16($fp)
    lw    $ra -4($fp)
    lw    $fp -8($fp)
    addi  $sp $sp 16
    jr    $ra

    .text
    .globl  Tree_accept_22
Tree_accept_22:
    sw    $ra -4($sp)
    sw    $fp -8($sp)
    move  $fp $sp
    addi  $sp $sp -8
    move  $t2 $a0
    move  $t1 $a1
    li    $a0 333
    jal   _print
    lw    $t0 ($t1)
    lw    $t0 ($t0)
    move  $a0 $t1
    move  $a1 $t2
    jalr  $t0
    li    $v0 0
    lw    $ra -4($fp)
    lw    $fp -8($fp)
    addi  $sp $sp 8
    jr    $ra

    .text
    .globl  Visitor_visit_23
Visitor_visit_23:
    sw    $ra -4($sp)
    sw    $fp -8($sp)
    move  $fp $sp
    addi  $sp $sp -16
    sw    $s0 -12($fp)
    sw    $s1 -16($fp)
    move  $s1 $a0
    move  $s0 $a1
    move  $a0 $s0
    jal   Tree_GetHas_Right_9
    move  $t0 $v0
    bne   $t0 1   L61
    move  $a0 $s0
    jal   Tree_GetRight_5
    move  $t0 $v0
    sw    $t0 4($s1)
    lw    $t0 4($s1)
    move  $a0 $t0
    move  $a1 $s1
    jal   Tree_accept_22
    b     L61
L61:move  $a0 $s0
    jal   Tree_GetHas_Left_10
    move  $t0 $v0
    bne   $t0 1   L63
    move  $a0 $s0
    jal   Tree_GetLeft_6
    move  $t0 $v0
    sw    $t0 8($s1)
    lw    $t0 8($s1)
    move  $a0 $t0
    move  $a1 $s1
    jal   Tree_accept_22
    b     L63
L63:li    $v0 0
    lw    $s0 -12($fp)
    lw    $s1 -16($fp)
    lw    $ra -4($fp)
    lw    $fp -8($fp)
    addi  $sp $sp 16
    jr    $ra

    .text
    .globl  MyVisitor_visit_24
MyVisitor_visit_24:
    sw    $ra -4($sp)
    sw    $fp -8($sp)
    move  $fp $sp
    addi  $sp $sp -16
    sw    $s0 -12($fp)
    sw    $s1 -16($fp)
    move  $s1 $a0
    move  $s0 $a1
    move  $a0 $s0
    jal   Tree_GetHas_Right_9
    move  $t0 $v0
    bne   $t0 1   L65
    move  $a0 $s0
    jal   Tree_GetRight_5
    move  $t0 $v0
    sw    $t0 4($s1)
    lw    $t0 4($s1)
    move  $a0 $t0
    move  $a1 $s1
    jal   Tree_accept_22
    b     L65
L65:move  $a0 $s0
    jal   Tree_GetKey_7
    move  $t0 $v0
    move  $a0 $t0
    jal   _print
    move  $a0 $s0
    jal   Tree_GetHas_Left_10
    move  $t0 $v0
    bne   $t0 1   L67
    move  $a0 $s0
    jal   Tree_GetLeft_6
    move  $t0 $v0
    sw    $t0 8($s1)
    lw    $t0 8($s1)
    move  $a0 $t0
    move  $a1 $s1
    jal   Tree_accept_22
    b     L67
L67:li    $v0 0
    lw    $s0 -12($fp)
    lw    $s1 -16($fp)
    lw    $ra -4($fp)
    lw    $fp -8($fp)
    addi  $sp $sp 16
    jr    $ra

    .text
    .globl  Visitor
Visitor:
    sw    $ra -4($sp)
    sw    $fp -8($sp)
    move  $fp $sp
    addi  $sp $sp -8
    li    $a0 4
    jal   _halloc
    move  $t1 $v0
    la    $t0 Visitor_visit_23
    sw    $t0 ($t1)
    move  $v0 $t1
    lw    $ra -4($fp)
    lw    $fp -8($fp)
    addi  $sp $sp 8
    jr    $ra

    .text
    .globl  MyVisitor
MyVisitor:
    sw    $ra -4($sp)
    sw    $fp -8($sp)
    move  $fp $sp
    addi  $sp $sp -8
    li    $a0 4
    jal   _halloc
    move  $t1 $v0
    la    $t0 MyVisitor_visit_24
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

