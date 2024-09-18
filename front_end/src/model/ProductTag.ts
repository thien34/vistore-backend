export interface ProductTagRequest {
    id?: number
    name: string
    productId: number
}

export interface ProductTagUpdateRequest {
    id: number
    name: string
}

export interface ProductTagResponse {
    id: number
    name: string
    productId: number
}
