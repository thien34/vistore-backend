export interface DiscountNameResponse {
    id: number
    name: string
}

export type DiscountRequest = {
    isActive: boolean
    name: string
    discountTypeId: number
    appliedToSubCategories: boolean
    usePercentage: boolean
    discountPercentage: number | null
    maxDiscountAmount: number | null
    requiresCouponCode: boolean
    couponCode: string | null
    discountLimitationId: number
    startDateUtc: string
    endDateUtc: string
    comment: string
    discountAmount: number | null
    isCumulative: boolean
    limitationTimes: number | null
    maxDiscountedQuantity: number | null
    minOderAmount: number | null
}

export type DiscountResponse = {
    id: number
    name: string
    discountTypeId: number
    discountTypeName: string
    appliedToSubCategories: boolean
    usePercentage: boolean
    discountPercentage: number | null
    maxDiscountAmount: number | null
    requiresCouponCode: boolean
    couponCode: string | null
    discountLimitationId: number
    startDateUtc: string
    endDateUtc: string
    comment: string
    discountAmount: number | null
    isCumulative: boolean
    limitationTimes: number | null
    maxDiscountedQuantity: number | null
    minOderAmount: number | null
    isActive: boolean
}
