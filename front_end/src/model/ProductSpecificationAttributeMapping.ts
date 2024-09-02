export type ProductSpecificationAttributeMappingRequest = {
    productId?: number
    specificationAttributeOptionId: number | null
    customValue: string
    showOnProductPage: boolean
    displayOrder: number
    specificationAttributeId?: number
}
export interface ProductSpecificationAttributeMappingResponse {
    id: number
    productId: number
    specificationAttributeOptionId: number | null
    specificationAttributeOptionName: string | null
    customValueJson: string | null
    customValue: string
    showOnProductPage: boolean
    displayOrder: number
    specificationAttributeId: number | null
    specificationAttributeName: string | null
}
export interface ProductSpecificationAttributeMappingByProductResponse {
    id: number
    attributeType: string
    specificationAttributeOptionName: string | null
    customValue: string
    showOnProductPage: boolean
    displayOrder: number
    specificationAttributeName: string | null
}
