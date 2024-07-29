import useCreateApi from '@/hooks/use-create-api'
import { ProductAttributeCombinationRequest } from '../../model/ProductAttributeCombination'
import { Form, FormProps } from 'antd'
import ProductAtbCombinationsConfig from './ProductAtbCombinationsConfig'
import { useQueryClient } from '@tanstack/react-query'
import { useState, useRef, useEffect } from 'react'
import useDeleteByIdApi from '@/hooks/use-delete-by-id-api'
import useUpdateApi from '@/hooks/use-update-api'

interface ProductAtbMapping {
    name: string
    type: string
    isRequired: boolean
    attributes: ProductAtbMappingValue[]
}

interface ProductAtbMappingValue {
    id: number
    value: string
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

    const data: ProductAtbMapping[] = [
        {
            name: 'RAM',
            type: 'select',
            isRequired: true,
            attributes: [
                { id: 1, value: '8GB' },
                { id: 2, value: '16GB' },
                { id: 3, value: '32GB' },
            ],
        },
        {
            name: 'HDD',
            type: 'radio',
            isRequired: true,
            attributes: [
                { id: 4, value: '1TB' },
                { id: 5, value: '2TB' },
                { id: 6, value: '512GB SSD' },
            ],
        },
        {
            name: 'Color',
            type: 'select',
            isRequired: false,
            attributes: [
                { id: 7, value: 'Red' },
                { id: 8, value: 'Blue' },
                { id: 9, value: 'Green' },
            ],
        },
        {
            name: 'Processor',
            type: 'radio',
            isRequired: false,
            attributes: [
                { id: 10, value: 'Intel i5' },
                { id: 11, value: 'Intel i7' },
                { id: 12, value: 'AMD Ryzen 5' },
            ],
        },
    ]

    const queryClient = useQueryClient()
    const [open, setOpen] = useState(false)
    const [selectedRecord, setSelectedRecord] = useState<ProductAttributeCombinationRequest | null>(null)

    const createApi = useCreateApi<ProductAttributeCombinationRequest, string>(ProductAtbCombinationsConfig.resourceUrl)
    const { mutate: deleteApi } = useDeleteByIdApi<number>(ProductAtbCombinationsConfig.resourceUrl)
    const updateApi = useUpdateApi<ProductAttributeCombinationRequest, string>(
        ProductAtbCombinationsConfig.resourceUrl,
        ProductAtbCombinationsConfig.resourceKey,
        Number(),
    )

    const handleCreate = (values: ProductAttributeCombinationRequest) => {
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

        createApi.mutate(productAttributeCombination, {
            onSuccess: () => {
                queryClient.invalidateQueries({
                    queryKey: [ProductAtbCombinationsConfig.resourceUrlByProductId, 'getById', 1],
                })
            },
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
        console.log('initialValues:', updatedInitialValues)

        setOpen(true)
    }

    return {
        data,
        initialValues,
        handleCreate,
        onFinishFailed,
        open,
        setOpen,
        handleDelete,
        selectedRecord,
        handleEdit,
    }
}

export default useProductAtbCombinationsViewModel
