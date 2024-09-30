import { useNavigate, useParams } from 'react-router-dom'
import CustomerConfigs from './CustomerConfigs'
import useUpdateApi from '@/hooks/use-update-api'
import useGetByIdApi from '@/hooks/use-get-by-id-api'
import useGetApi from '@/hooks/use-get-api'
import { CustomerFullRequest, CustomerFullResponse } from '@/model/Customer'
import { CustomerRoleResponse } from '@/model/CustomerRole'
import dayjs from 'dayjs'
import ManagerPath from '@/constants/ManagerPath'
import { useEffect, useState } from 'react'
import { Form, Modal, RadioChangeEvent } from 'antd'
import CustomerRoleConfigs from '../customer-roles/CustomerRoleConfigs'
import useDeleteByIdApi from '@/hooks/use-delete-by-id-api'
import getAddressColumns from '../address/AddressColumns.tsx'
import useGetAllApi from '@/hooks/use-get-all-api'
import { AddressResponse } from '@/model/Address'
import AddressConfigs from '../address/AddressConfigs.ts'
import { RequestParams } from '@/utils/FetchUtils'
import { TableRowSelection } from 'antd/es/table/interface'
import useDeleteByIdsApi from '@/hooks/use-delete-by-ids-api.ts'

interface Search extends RequestParams {}
export type ExtendedRequestParams = RequestParams & {
    customerId?: number
}

function useCustomerUpdateViewModel() {
    const { id } = useParams<{ id: string }>()
    const [form] = Form.useForm()
    const navigate = useNavigate()
    const [error, setError] = useState<string | null>(null)
    const [value, setValue] = useState('MALE')
    const [customerRoles, setCustomerRoles] = useState<CustomerRoleResponse[]>([])
    const [selectedRowKeys, setSelectedRowKeys] = useState<React.Key[]>([])
    const [filter, setFilter] = useState<Search>({})

    const { data, isSuccess } = useGetByIdApi<CustomerFullResponse>(
        CustomerConfigs.resourceUrl,
        CustomerConfigs.resourceKey,
        Number(id),
    )
    const { data: rolesData } = useGetApi<CustomerRoleResponse[]>(
        `${CustomerRoleConfigs.resourceUrl}/list-name`,
        CustomerRoleConfigs.resourceKey,
    )
    const {
        data: addressesResponse,
        isLoading,
        refetch,
    } = useGetAllApi<AddressResponse>(AddressConfigs.resourceUrl, AddressConfigs.resourceKey, {
        customerId: Number(id),
        ...filter,
    } as ExtendedRequestParams)

    const { mutate: updateApi } = useUpdateApi<CustomerFullRequest>(
        CustomerConfigs.resourceUrl,
        CustomerConfigs.resourceKey,
        Number(id),
    )
    const { mutate: deleteCustomer } = useDeleteByIdApi<number>(
        CustomerConfigs.resourceUrl,
        CustomerConfigs.resourceKey,
    )
    const { mutate: deleteApi } = useDeleteByIdsApi<number>(AddressConfigs.resourceUrl, AddressConfigs.resourceKey)
    useEffect(() => {
        document.title = 'Update a new customer - Vítore'
    }, [])
    const handleTableChange = (pagination: { current: number; pageSize: number }) => {
        setFilter((prevFilter) => ({
            ...prevFilter,
            pageNo: pagination.current,
            pageSize: pagination.pageSize,
        }))
    }
    useEffect(() => {
        if (isSuccess && data) {
            form.setFieldsValue({
                ...data,
                dateOfBirth: data.dateOfBirth ? dayjs(data.dateOfBirth) : null,
                customerRoles: data.customerRoles || [],
            })
        } else if (!isSuccess) {
            setError('Failed to fetch customer data.')
        }

        if (rolesData) {
            setCustomerRoles(rolesData)
        }
    }, [isSuccess, data, form, rolesData])
    const onFinish = (values: CustomerFullRequest) => {
        const customerRequest: CustomerFullRequest = {
            ...values,
        }
        updateApi(customerRequest, {
            onSuccess: () => {
                navigate(ManagerPath.CUSTOMER)
            },
        })
    }
    const handleDelete = () => {
        if (Number(id)) {
            Modal.confirm({
                title: 'Are you sure you want to delete this item?',
                content: 'This action cannot be undone.',
                okText: 'Delete',
                okType: 'danger',
                cancelText: 'Cancel',
                onOk: () => {
                    deleteCustomer(Number(id), {
                        onSuccess: () => {
                            navigate(ManagerPath.CUSTOMER)
                        },
                    })
                },
            })
        }
    }
    const onChange = (e: RadioChangeEvent) => {
        setValue(e.target.value)
    }
    const handleAddAddress = () => {
        navigate(`${ManagerPath.ADDRESS_ADD}?customerId=${id}`)
    }
    const onSelectChange = (newSelectedRowKeys: React.Key[]) => {
        setSelectedRowKeys(newSelectedRowKeys)
    }
    const columns = getAddressColumns()
    const rowSelection: TableRowSelection<AddressResponse> = {
        selectedRowKeys,
        onChange: onSelectChange,
    }
    const handleDeleteAddress = async () => {
        if (selectedRowKeys.length === 0) {
            Modal.warning({
                title: 'No addresses selected',
                content: 'Please select at least one address to delete.',
                okText: 'OK',
            })
            return
        }
        Modal.confirm({
            title: 'Are you sure you want to delete the selected addresses?',
            content: 'This action cannot be undone. Once deleted, the selected addresses will be permanently removed.',
            okText: 'Yes, Delete',
            okType: 'danger',
            cancelText: 'Cancel',
            onOk: () => {
                deleteApi(selectedRowKeys as number[], {
                    onSuccess: () => {
                        refetch()
                        setSelectedRowKeys([])
                    },
                })
            },
        })
    }

    return {
        form,
        onFinish,
        error,
        value,
        columns,
        onChange,
        customerRoles,
        handleDelete,
        isLoading,
        rowSelection,
        filter,
        addressesResponse,
        handleTableChange,
        handleDeleteAddress,
        handleAddAddress,
    }
}

export default useCustomerUpdateViewModel
