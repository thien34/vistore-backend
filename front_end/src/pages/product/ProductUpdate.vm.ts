import useGetByIdApi from '@/hooks/use-get-by-id-api'
import { ProductDetailResponse } from '@/model/Product'
import { useQueryClient } from '@tanstack/react-query'
import { useDispatch, useSelector } from 'react-redux'
import { useNavigate, useParams } from 'react-router-dom'
import ProductConfigs from './ProductConfigs'
import { useEffect } from 'react'
import { setProduct } from '@/slice/productSlice'
import { RootState } from '@/redux/store/productStore'
import useDeleteByIdApi from '@/hooks/use-delete-by-id-api'
import { Modal } from 'antd'

function useProductUpdateViewModel() {
    const queryClient = useQueryClient()
    const dispatch = useDispatch()
    const { productId: productId } = useParams<{ productId: string }>()
    const navigate = useNavigate()
    const product = useSelector((state: RootState) => state.product)

    const deleteProductApi = useDeleteByIdApi<number>(ProductConfigs.resourceUrl, ProductConfigs.resourceKey)
    const { refetch: fetchProductDetail } = useGetByIdApi<ProductDetailResponse>(
        ProductConfigs.resourceUrl,
        ProductConfigs.resourceKey,
        productId ? parseInt(productId) : null,
    )

    useEffect(() => {
        const fetchProductDetailData = async () => {
            if (!productId) return
            await queryClient.invalidateQueries({ queryKey: ['product', productId] })
            const { data: productDetail } = await fetchProductDetail()

            if (productDetail) {
                dispatch(setProduct(productDetail))
            }
        }

        fetchProductDetailData()
    }, [productId, queryClient, fetchProductDetail, dispatch])

    const handleDeleteProduct = () => {
        if (product.id) {
            Modal.confirm({
                title: 'Are you sure you want to delete this item?',
                content: 'This action cannot be undone.',
                okText: 'Delete',
                okType: 'danger',
                cancelText: 'Cancel',
                onOk: () => {
                    if (product.id) {
                        deleteProductApi.mutate(product.id, {
                            onSuccess: () => {
                                navigate('/admin/products')
                            },
                        })
                    }
                },
            })
        }
    }

    return { handleDeleteProduct }
}

export default useProductUpdateViewModel
