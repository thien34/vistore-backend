export type StockQuantityHistoryRequest = {
    id?: number
    productId: number
    quantityAdjustment: number
    stockQuantity: number
    message: string
}
export type StockQuantityHistoryResponse = {
    id: number
    productId: number
    quantityAdjustment: number
    stockQuantity: number
    message: string
    createdDate: Date
}
