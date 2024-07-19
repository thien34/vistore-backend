import ProductAttributeConfigs from './ProductAttributeConfigs'
import useCreateApi from '@/hooks/use-create-api.ts'
import { ProductAttributeRequest } from '@/model/ProductAttribute.ts'
import { message } from 'antd'
import { useNavigate } from 'react-router-dom'
function useProductAttributeCreate() {
    const { mutate: createProductAttribute } = useCreateApi<ProductAttributeRequest, string>(
        ProductAttributeConfigs.resourceUrl,
    )
    const navigation = useNavigate()
    const onFinish = async (values: ProductAttributeRequest) => {
        console.log(values)
        createProductAttribute(values, {
            onSuccess: (data: string) => {
                console.log('data tra ve thanh cong: ', data)
                message.success(data)
                navigation(-1)
            },
            onError: (error: { message: string }) => {
                console.log('data tra ve loi: ', error)
                message.error(error.message)
            },
        })
    }

    return {
        onFinish,
    }
}
export default useProductAttributeCreate
