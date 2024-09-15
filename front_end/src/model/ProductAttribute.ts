import {
    PredefinedProductAttributeValueRequest,
    PredefinedProductAttributeValueResponse,
} from '@/model/PredefinedProductAttributeValue.ts'

export type ProductAttributeRequest = {
    id?: number
    name: string
    description?: string
    values?: PredefinedProductAttributeValueRequest[]
}

export type ProductAttributeResponse = {
    values: Array<PredefinedProductAttributeValueResponse>
    id: number
    name: string
    description: string
}

export type ProductAttributeNameResponse = {
    id: number
    name: string
}

export interface ProductRequest {
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
