export type ProductVideoMappingResponse = {
    id: number
    videoUrl: string
    displayOrder: number
    productId?: number
}
export type ProductVideoMappingRequest = {
    videoUrl: string
    displayOrder: number
    productId: number
}
