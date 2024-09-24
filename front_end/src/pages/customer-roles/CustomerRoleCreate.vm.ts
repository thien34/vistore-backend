import useCreateApi from '@/hooks/use-create-api'
import { CustomerRoleRequest } from '@/model/CustomerRole'
import CustomerRoleConfigs from './CustomerRoleConfigs'
import { Form } from 'antd'
import { useNavigate } from 'react-router-dom'
import ManagerPath from '@/constants/ManagerPath'

function useCustomerRoleCreateViewModel() {
    const [form] = Form.useForm<CustomerRoleRequest>()
    const navigate = useNavigate()

    const { mutate: createCustomerRole } = useCreateApi<CustomerRoleRequest>(CustomerRoleConfigs.resourceUrl)

    const onFinish = (values: CustomerRoleRequest, redirect: boolean) => {
        createCustomerRole(values, {
            onSuccess: () => {
                if (redirect) {
                    navigate(ManagerPath.CUSTOMER_ROLE)
                }
                form.resetFields()
            },
        })
    }

    return { form, onFinish }
}

export default useCustomerRoleCreateViewModel
