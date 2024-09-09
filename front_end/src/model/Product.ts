export type ProductRequest = {
    name: string
}

export type ProductResponse = {
    id: number
    name: string
}

export interface ProductNameResponse {
    id: number
    name: string
}

export type ProductDetailResponse = {
    name: string
    shortDescription: string
    fullDescription: string
    sku: string
    categories: number[]
    manufacturers: number[]
    published: boolean
    productTags: string[]
    gtin: string
    manufacturePartNumber: string
    showOnHomePage: boolean
    allowCustomerReviews: boolean
    availableStartDate: string | null
    availableEndDate: string | null
    markAsNew: boolean
    availableEndDateTimeUtc: string | null
    markAsNewStartDateTimeUtc: string | null
    adminComment: string
    id: number
    approvedRatingSum: number
    notApprovedRatingSum: number
    approveTotalReviews: number
    notApprovedTotalReviews: number
    isShipEnabled: boolean
    isFreeShipping: boolean
    productAvailabilityRangeId: number
    displayStockAvailability: boolean
    displayStockQuantity: boolean
    minStockQuantity: number
    notReturnable: boolean
    unitPrice: number
    oldPrice: number
    productCost: number
    weight: number
    length: number
    width: number
    height: number
    displayOrder: number
    deleted: boolean
    categoryIds: number[]
    manufacturerIds: number[]
    minCartQty: number
    maxCartQty: number
    manageInventoryMethodId: number
}
