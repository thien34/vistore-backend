import { useNavigate } from 'react-router-dom'
import useDeleteByIdApi from '@/hooks/use-delete-by-id-api'
import { Modal } from 'antd'
import ProductConfigs from './ProductConfigs'
import { useSelector } from 'react-redux'
import { RootState } from '@/redux/store/productStore'

function useProductUpdateViewModelV2() {
    const navigate = useNavigate()
    const product = useSelector((state: RootState) => state.product)

    const deleteProductApi = useDeleteByIdApi<number>(ProductConfigs.resourceUrl, ProductConfigs.resourceKey)

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
export default useProductUpdateViewModelV2
