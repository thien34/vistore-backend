export interface ProductTagRequest {
    id?: number
    name: string
    productId: number
}

export interface ProductTagResponse {
    id: number
    name: string
    productId: number
}
