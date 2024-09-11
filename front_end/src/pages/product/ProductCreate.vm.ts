import useCreateApi from '@/hooks/use-create-api'
import { ProductRequest } from '@/model/ProductAttribute'
import ProductConfigs from './ProductConfigs'
import { useDispatch, useSelector } from 'react-redux'
import { RootState } from '@/redux/store/productStore'
import { useNavigate } from 'react-router-dom'
import { useState } from 'react'
import { resetProductState, setProduct, setProductErrors } from '@/slice/productSlice'
import { CheckboxChangeEvent } from 'antd/es/checkbox'

function useProductCreateViewModel() {
    const createApi = useCreateApi<ProductRequest>(ProductConfigs.resourceUrl)
    const product = useSelector((state: RootState) => state.product)
    const [errors, setErrors] = useState<Record<string, string>>({})
    const navigate = useNavigate()
    const dispatch = useDispatch()

    const handleCreate = () => {
        if (validateProduct()) {
            const productRequest: ProductRequest = {
                ...product,
                fullDescription: product.lastContent ?? '',
                categories: [],
                manufacturers: [],
                availableStartDate: new Date().toISOString(),
                availableEndDate: new Date().toISOString(),
            }
            createApi.mutate(productRequest, {
                onSuccess: () => {
                    dispatch(resetProductState())
                    navigate('/admin/products')
                },
            })
        }
    }

    const handleSaveAndContinueEdit = () => {
        if (validateProduct()) {
            const productRequest: ProductRequest = {
                ...product,
                categories: [],
                manufacturers: [],
                availableStartDate: new Date().toISOString(),
                availableEndDate: new Date().toISOString(),
            }
            createApi.mutate(productRequest, {
                onSuccess: (response) => {
                    const newProductId = response.data
                    navigate(`/admin/products/${newProductId}`)
                },
            })
        }
    }

    const validateProduct = () => {
        const newErrors: Record<string, string> = {}
        if (!product.name || product.name.trim() === '') {
            newErrors.name = 'Product name cannot be empty'
        }

        dispatch(setProductErrors(newErrors))

        return Object.keys(newErrors).length === 0
    }
    const clearError = (fieldName: string) => {
        dispatch(setProductErrors({ ...errors, [fieldName]: '' }))
    }

    const handleMarkAsNewChange = (e: { target: { checked: boolean } }) => {
        dispatch(setProduct({ markAsNew: e.target.checked }))
    }

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
        const { name, value } = e.target
        dispatch(setProduct({ [name]: value }))
        if (errors[name]) {
            clearError(name)
        }
    }

    const handleCategoriesChange = (selectedValues: number[]) => {
        dispatch(setProduct({ categoryIds: selectedValues }))
    }

    const handleManufacturerChange = (selectedValues: number[]) => {
        dispatch(setProduct({ manufacturerIds: selectedValues }))
    }

    const handleProductTagsChange = (selectedValues: string[]) => {
        dispatch(setProduct({ productTags: selectedValues }))
    }

    const handleCheckboxChange = (e: CheckboxChangeEvent) => {
        const { name, checked } = e.target as { name: string; checked: boolean }
        dispatch(setProduct({ [name]: checked }))
    }
    const handleDiscountsChange = (selectedValues: number[]) => {
        dispatch(setProduct({ discountIds: selectedValues }))
    }

    return {
        handleCreate,
        handleSaveAndContinueEdit,
        errors,
        setErrors,
        clearError,
        handleMarkAsNewChange,
        handleInputChange,
        handleCategoriesChange,
        handleManufacturerChange,
        handleProductTagsChange,
        handleCheckboxChange,
        handleDiscountsChange,
    }
}
export default useProductCreateViewModel
