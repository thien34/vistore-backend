export interface ProductAttributeCombinationRequest {
    id: number
    stockQuantity?: number
    minStockQuantity?: number
    overriddenPrice?: number
    allowOutOfStockOrders?: false
    sku: string
    manufacturerPartNumber: string
    gtin: string
    pictureIds: []
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    [key: string]: any
}
export interface ProductAttributeCombinationResponse {
    id: number
    stockQuantity?: number
    minStockQuantity?: number
    overriddenPrice?: number
    allowOutOfStockOrders?: false
    sku: string
    manufacturerPartNumber: string
    gtin: string
    pictureUrl: string
    attributesXml: string
    pictureIds: []
}
