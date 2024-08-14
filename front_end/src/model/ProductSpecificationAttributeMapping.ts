export type ProductSpecificationAttributeMappingRequest = {
    productId?: number
    specificationAttributeOptionId: number
    customValue: string
    showOnProductPage: boolean
    displayOrder: number
    specificationAttributeId: number
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
