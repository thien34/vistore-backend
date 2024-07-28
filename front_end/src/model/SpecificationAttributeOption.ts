export type SpecificationAttributeOptionRequest = {
    id?: number
    specificationAttribute: number | null
    name: string
    colorSquaresRgb: string
    displayOrder: number
}
export type SpecificationAttributeOptionResponse = {
    id: number
    specificationAttribute: number | null
    name: string
    colorSquaresRgb: string
    displayOrder: number
}
