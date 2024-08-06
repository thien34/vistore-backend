export type ProductSpecificationAttributeMappingRequest = {
    productId?: number
    specificationAttributeOptionId: number
    customValue: string
    showOnProductPage: boolean
    displayOrder: number
    specificationAttributeId: number
    attributeType: string
}
export type ProductSpecificationAttributeMappingResponse = {
    id: number
    productId: number
    specificationAttributeOptionId: number
    customValue: string
    showOnProductPage: boolean
    displayOrder: number
    specificationAttributeId: number
}
