import useCreateApi from '@/hooks/use-create-api'
import { ProductRequest } from '@/model/ProductAttribute'
import ProductConfigs from './ProductConfigs'
import { useDispatch, useSelector } from 'react-redux'
import { RootState } from '@/redux/store/productStore'
import { useNavigate } from 'react-router-dom'
import { useState } from 'react'
import { resetProductState, setProductErrors } from '@/slice/productSlice'

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
                    dispatch(resetProductState())
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

    return {
        handleCreate,
        handleSaveAndContinueEdit,
        errors,
        setErrors,
        clearError,
    }
}
export default useProductCreateViewModel
