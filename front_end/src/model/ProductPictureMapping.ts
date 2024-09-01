export type ProductPictureMappingResponse = {
    id: number
    pictureUrl?: string
    displayOrder: number
    productId: number
}
export interface ProductPictureMappingRequest {
    displayOrder: number
    productId: number
}
