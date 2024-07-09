export type PredefinedProductAttributeValueRequest = {
    id?: number
    productAttribute: number | null
    name: string
    priceAdjustment: number
    priceAdjustmentUsePercentage: boolean
    weightAdjustment: number
    cost: number
    isPreSelected: boolean
    displayOrder: number
}