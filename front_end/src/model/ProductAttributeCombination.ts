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
    pictureUrl: String
    attributesXml: string
    pictureIds: []
}
