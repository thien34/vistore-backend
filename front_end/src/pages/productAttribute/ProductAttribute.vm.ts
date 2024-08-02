import { TableRowSelection } from 'antd/es/table/interface'
// import  {getProductAttributeColumns} from './ProductAttributeColumns'
import React, { useState } from 'react'
import { RequestParams } from '@/utils/FetchUtils'
import useGetAllApi from '@/hooks/use-get-all-api'
import ProductAttributeConfigs from './ProductAttributeConfigs'
import useDeleteByIdsApi from '@/hooks/use-delete-by-ids-api'
import { ProductAttributeResponse } from '@/model/ProductAttribute.ts'
import { PredefinedProductAttributeValueRequest } from '@/model/PredefinedProductAttributeValue.ts'
import { Form } from 'antd'

interface Search extends RequestParams {
    published?: boolean
}

function useProductAttributeViewModel() {
    const [selectedRowKeys, setSelectedRowKeys] = useState<React.Key[]>([])
    const [filter, setFilter] = useState<Search>({})
    const [isOpenList, setIsOpenList] = useState(false)
    const [dataDetail, setDataDetail] = useState<Array<PredefinedProductAttributeValueRequest>>([])
    const [form] = Form.useForm()
    const [editingKey, setEditingKey] = useState('')

    const isEditing = (record: PredefinedProductAttributeValueRequest) => record.id === Number(editingKey)
    const cancel = () => {
        setEditingKey('')
    }
    const setShowList = (record: ProductAttributeResponse) => {
        setIsOpenList(true)
        setDataDetail(record?.values)
    }
    const { mutate: deleteApi } = useDeleteByIdsApi<number>(
        ProductAttributeConfigs.resourceUrl,
        ProductAttributeConfigs.resourceKey,
    )

    // HANDLE SELECT CHANGE
    const onSelectChange = (newSelectedRowKeys: React.Key[]) => {
        setSelectedRowKeys(newSelectedRowKeys)
    }

    const rowSelection: TableRowSelection<ProductAttributeResponse> = {
        selectedRowKeys,
        onChange: onSelectChange,
    }

    // RETURN DATA
    const {
        data: listResponse,
        isLoading,
        refetch,
    } = useGetAllApi<ProductAttributeResponse>(
        ProductAttributeConfigs.resourceUrl,
        ProductAttributeConfigs.resourceKey,
        filter,
    )

    // HANDLE DELETE
    const handleDelete = async () => {
        deleteApi(selectedRowKeys as number[], {
            onSuccess: () => {
                refetch()
                setSelectedRowKeys([])
            },
        })
    }

    // HANDLE TABLE CHANGE
    const handleTableChange = (pagination: { current: number; pageSize: number }) => {
        setFilter((prevFilter) => ({
            ...prevFilter,
            pageNo: pagination.current - 1,
        }))
    }

    // HANDLE SEARCH
    const handleSearch = (newFilter: { name: string; published: boolean | undefined }) => {
        setFilter((prevFilter) => ({
            ...prevFilter,
            ...newFilter,
            pageNo: 0,
        }))
    }

    return {
        rowSelection,
        handleTableChange,
        handleSearch,
        handleDelete,
        selectedRowKeys,
        filter,
        listResponse,
        isLoading,
        isOpenList,
        setIsOpenList,
        dataDetail,
        setDataDetail,
        form,
        isEditing,
        cancel,
        setShowList,
    }
}
export default useProductAttributeViewModel
