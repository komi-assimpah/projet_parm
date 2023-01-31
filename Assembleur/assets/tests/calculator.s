	.text
	.syntax unified
	.eabi_attribute	67, "2.09"	@ Tag_conformance
	.cpu	cortex-m0
	.eabi_attribute	6, 12	@ Tag_CPU_arch
	.eabi_attribute	8, 0	@ Tag_ARM_ISA_use
	.eabi_attribute	9, 1	@ Tag_THUMB_ISA_use
	.eabi_attribute	17, 1	@ Tag_ABI_PCS_GOT_use
	.eabi_attribute	20, 1	@ Tag_ABI_FP_denormal
	.eabi_attribute	21, 1	@ Tag_ABI_FP_exceptions
	.eabi_attribute	23, 3	@ Tag_ABI_FP_number_model
	.eabi_attribute	34, 0	@ Tag_CPU_unaligned_access
	.eabi_attribute	24, 1	@ Tag_ABI_align_needed
	.eabi_attribute	25, 1	@ Tag_ABI_align_preserved
	.eabi_attribute	38, 1	@ Tag_ABI_FP_16bit_format
	.eabi_attribute	18, 4	@ Tag_ABI_PCS_wchar_t
	.eabi_attribute	26, 2	@ Tag_ABI_enum_size
	.eabi_attribute	14, 0	@ Tag_ABI_PCS_R9_use
	.file	"demo.c"
	.globl	main
	.p2align	1
	.type	main,%function
	.code	16                      @ @main
	.thumb_func
main:
	.fnstart
@ BB#0:
	.pad	#24
	sub	sp, #24
	movs	r0, #0
	str	r0, [sp, #20]
	str	r0, [sp, #16]
	str	r0, [sp, #12]
	movs	r0, #1
	str	r0, [sp, #8]
	movs	r0, #2
	str	r0, [sp, #4]
	movs	r0, #3
	str	r0, [sp]
	b	.LBB0_1
.LBB0_1:                                @ =>This Inner Loop Header: Depth=1
	ldr	r0, [sp, #32]
	ldr	r1, [sp, #12]
	cmp	r0, r1
	bne	.LBB0_3
	b	.LBB0_2
.LBB0_2:                                @   in Loop: Header=BB0_1 Depth=1
	ldr	r0, [sp, #24]
	ldr	r1, [sp, #28]
	adds	r0, r0, r1
	str	r0, [sp, #40]
	b	.LBB0_3
.LBB0_3:                                @   in Loop: Header=BB0_1 Depth=1
	ldr	r0, [sp, #32]
	ldr	r1, [sp, #8]
	cmp	r0, r1
	bne	.LBB0_5
	b	.LBB0_4
.LBB0_4:                                @   in Loop: Header=BB0_1 Depth=1
	ldr	r0, [sp, #24]
	ldr	r1, [sp, #28]
	subs	r0, r0, r1
	str	r0, [sp, #40]
	b	.LBB0_5
.LBB0_5:                                @   in Loop: Header=BB0_1 Depth=1
	ldr	r0, [sp, #32]
	ldr	r1, [sp, #4]
	cmp	r0, r1
	bne	.LBB0_7
	b	.LBB0_6
.LBB0_6:                                @   in Loop: Header=BB0_1 Depth=1
	ldr	r0, [sp, #24]
	ldr	r1, [sp, #28]
	muls	r1, r0, r1
	str	r1, [sp, #40]
	b	.LBB0_7
.LBB0_7:                                @   in Loop: Header=BB0_1 Depth=1
	ldr	r0, [sp, #32]
	ldr	r1, [sp]
	cmp	r0, r1
	bne	.LBB0_9
	b	.LBB0_8
.LBB0_8:                                @   in Loop: Header=BB0_1 Depth=1
	ldr	r0, [sp, #24]
	ldr	r1, [sp, #28]
	lsls	r0, r1
	str	r0, [sp, #40]
	b	.LBB0_9
.LBB0_9:                                @   in Loop: Header=BB0_1 Depth=1
	b	.LBB0_1
.Lfunc_end0:
	.size	main, .Lfunc_end0-main
	.cantunwind
	.fnend


	.ident	"clang version 4.0.1 (tags/RELEASE_401/final)"
	.section	".note.GNU-stack","",%progbits
	.eabi_attribute	30, 5	@ Tag_ABI_optimization_goals
