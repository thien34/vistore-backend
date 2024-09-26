export type ProductProductAttributeMappingRequest = {
    id?: number
    productId: number
    productAttributeId: number
    textPrompt: string
    isRequired: boolean
    attributeControlTypeId: number
    displayOrder: number
    productAttributeValueRequests: ProductAttributeValueRequest[] | null
}

export type ProductAttributeValueRequest = {
    id?: number | null
    productAttributeMappingId?: number
    name: string
    colorSquaresRgb: string
    priceAdjustment: number
    priceAdjustmentPercentage: boolean
    weightAdjustment: number
    cost: number
    isPreSelected: boolean
    displayOrder: number
    productAttributeValuePictureRequests: ProductAttributeValuePictureRequest[]
}

export type ProductAttributeValueResponse = {
    id: number
    name: string
    colorSquaresRgb: string
    priceAdjustment: number
    priceAdjustmentPercentage: boolean
    weightAdjustment: number
    cost: number
    isPreSelected: boolean
    displayOrder: number
    imageUrl: string[]
}

export type ProductAttributeValuePictureRequest = {
    id?: number
    productAttributeValueId: number
    pictureId: number
}

export type ProductProductAttributeMappingResponse = {
    id: number
    nameProductAttribute: string
    textPrompt: string
    isRequired: boolean
    attributeControlTypeId: string
    displayOrder: number
}

export type ProductProductAttributeMappingDetailResponse = {
    id: number
    productId: number
    productAttributeId: number
    textPrompt: string
    isRequired: boolean
    attributeControlTypeId: string
    displayOrder: number
    productAttributeValueResponses: ProductAttributeValueResponse[]
}
