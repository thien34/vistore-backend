import useUpdateApi from '@/hooks/use-update-api'
import { useNavigate, useParams } from 'react-router-dom'
import useGetByIdApi from '@/hooks/use-get-by-id-api'
import { ProductAttributeResponse } from '@/model/ProductAttribute.ts'
import ProductAttributeConfigs from './ProductAttributeConfigs.ts'

function useProductAttributeUpdate() {
    const navigation = useNavigate()
    const { id } = useParams<{ id: string }>()

    const { mutate: updateProductAttribute, isPending } = useUpdateApi<ProductAttributeResponse, string>(
        ProductAttributeConfigs.resourceUrl,
        ProductAttributeConfigs.resourceKey,
        Number(id),
    )

    const { data: productAttributeResponse, isLoading } = useGetByIdApi<ProductAttributeResponse>(
        ProductAttributeConfigs.resourceUrl,
        ProductAttributeConfigs.resourceKey,
        Number(id),
    )

    // HANDLER FUNCTION ON FINISH FORM
    const onFinish = async (values: ProductAttributeResponse) => {
        updateProductAttribute({ id: Number(id), ...values }, { onSuccess: () => navigation(-1) })
    }
    return {
        onFinish,
        productAttributeResponse,
        isLoading,
        isPending,
    }
}
export default useProductAttributeUpdate
