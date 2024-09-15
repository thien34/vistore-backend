export type RelatedProductResponse = {
    id: number
    product1Id: number
    product2Id: number
    nameProduct2: string
    displayOrder: number
}
export type RelatedProductRequest = {
    id?: number
    product1Id: number
    product2Id: number
    displayOrder: number
}