export type ProductVideoMappingResponse = {
    id: number
    videoUrl?: string
    videoFile?: File
    displayOrder: number
    productId?: number
}
export interface ProductVideoMappingRequest {
    videoUrl?: string
    displayOrder: number
    productId: number
    isUpload: boolean
    videoFile?: File
    inputMethod: 'url' | 'upload'
}
