import useCreateApi from '@/hooks/use-create-api'
import { ProductAttributeCombinationRequest } from '../../model/ProductAttributeCombination'
import { Form, FormProps } from 'antd'
import ProductAtbCombinationsConfig from './ProductAtbCombinationsConfig'
import { useQueryClient } from '@tanstack/react-query'
import { useState } from 'react'
import useDeleteByIdApi from '@/hooks/use-delete-by-id-api'

export interface ProductAtbMapping {
    attName: string
    attributeControlTypeId: string
    isRequired: boolean
    productAttributeValueResponses: ProductAtbMappingValue[]
}

interface ProductAtbMappingValue {
    id: number
    name: string
}

function useProductAtbCombinationsViewModel() {
    const [form] = Form.useForm()

    const initialValues: { [key: string]: any } = {
        id: null,
        stockQuantity: 1000,
        minStockQuantity: 0,
        allowOutOfStockOrders: false,
        sku: '',
        manufacturerPartNumber: '',
        gtin: '',
        overriddenPrice: 0,
        pictureIds: [],
    }

    // const data: ProductAtbMapping[] = [
    //     {
    //         name: 'RAM',
    //         type: 'select',
    //         isRequired: true,
    //         attributes: [
    //             { id: 1, value: '8GB' },
    //             { id: 2, value: '16GB' },
    //             { id: 3, value: '32GB' },
    //         ],
    //     },
    //     {
    //         name: 'HDD',
    //         type: 'radio',
    //         isRequired: true,
    //         attributes: [
    //             { id: 4, value: '1TB' },
    //             { id: 5, value: '2TB' },
    //             { id: 6, value: '512GB SSD' },
    //         ],
    //     },
    //     {
    //         name: 'Color',
    //         type: 'select',
    //         isRequired: false,
    //         attributes: [
    //             { id: 7, value: 'Red' },
    //             { id: 8, value: 'Blue' },
    //             { id: 9, value: 'Green' },
    //         ],
    //     },
    //     {
    //         name: 'Processor',
    //         type: 'radio',
    //         isRequired: false,
    //         attributes: [
    //             { id: 10, value: 'Intel i5' },
    //             { id: 11, value: 'Intel i7' },
    //             { id: 12, value: 'AMD Ryzen 5' },
    //         ],
    //     },
    // ]

    const queryClient = useQueryClient()
    const [open, setOpen] = useState(false)
    const [selectedRecord, setSelectedRecord] = useState<ProductAttributeCombinationRequest | null>(null)
    const [error, setError] = useState('')
    const createApi = useCreateApi<ProductAttributeCombinationRequest, string>(ProductAtbCombinationsConfig.resourceUrl)
    const { mutate: deleteApi } = useDeleteByIdApi<number>(ProductAtbCombinationsConfig.resourceUrl)

    const handleCreate = async (values: ProductAttributeCombinationRequest) => {
        const attributes = Object.keys(values)
            .filter(
                (key) =>
                    key !== 'stockQuantity' &&
                    key !== 'allowOutOfStockOrders' &&
                    key !== 'sku' &&
                    key !== 'manufacturerPartNumber' &&
                    key !== 'gtin' &&
                    key !== 'overriddenPrice' &&
                    key !== 'minStockQuantity' &&
                    key !== 'productId' &&
                    key !== 'pictureIds' &&
                    key !== 'id' &&
                    key !== '',
            )
            .map((key) => ({ [key]: values[key] }))

        const attributesXml = JSON.stringify({ attributes })

        const productAttributeCombination = {
            ...values,
            attributesXml,
            productId: 1,
        }

        return new Promise<void>((resolve, reject) => {
            createApi.mutate(productAttributeCombination, {
                onSuccess: async () => {
                    await queryClient.invalidateQueries({
                        queryKey: [ProductAtbCombinationsConfig.resourceUrlByProductId, 'getById', 1],
                    })
                    setError('')
                    resolve()
                },
                onError: (error) => {
                    setError(error.message)
                    reject(error)
                },
            })
        })
    }

    const onFinishFailed: FormProps<ProductAttributeCombinationRequest>['onFinishFailed'] = (errorInfo) => {
        console.log('Failed:', errorInfo)
    }

    const handleDelete = (id: number) => {
        deleteApi(id, {
            onSuccess: () => {
                queryClient.invalidateQueries({
                    queryKey: [ProductAtbCombinationsConfig.resourceUrlByProductId, 'getById', 1],
                })
            },
        })
    }

    const handleEdit = (record: ProductAttributeCombinationRequest) => {
        setSelectedRecord(record)
        const updatedInitialValues = {
            ...initialValues,
            ...record,
        }
        form.setFieldsValue(updatedInitialValues)

        setOpen(true)
    }

    const handleOpenModal = () => {
        form.resetFields()
        setSelectedRecord(null)
        setOpen(true)
    }

    return {
        initialValues,
        handleCreate,
        onFinishFailed,
        open,
        setOpen,
        handleDelete,
        selectedRecord,
        handleEdit,
        error,
        setError,
        handleOpenModal,
    }
}

export default useProductAtbCombinationsViewModel
