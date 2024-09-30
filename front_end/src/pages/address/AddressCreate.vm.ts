import { useEffect, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import useCreateApi from '@/hooks/use-create-api'
import useGetApi from '@/hooks/use-get-api'
import { AddressRequest, District, Province, Ward } from '@/model/Address'
import AddressConfigs from './AddressConfigs'
import { Form } from 'antd'
import ManagerPath from '@/constants/ManagerPath'

function useAddressCreateViewModel() {
    const { customerId: paramCustomerId } = useParams<{ customerId: string }>()
    const queryParams = new URLSearchParams(location.search)
    const customerId = queryParams.get('customerId') || paramCustomerId
    const [filteredDistricts, setFilteredDistricts] = useState<District[]>([])
    const [filteredWards, setFilteredWards] = useState<Ward[]>([])
    const [form] = Form.useForm()
    const navigate = useNavigate()

    const { mutate: createAddress } = useCreateApi<AddressRequest>(AddressConfigs.resourceUrl)

    const { data: provinces } = useGetApi<Province[]>(
        AddressConfigs.resourceUrlProvince,
        AddressConfigs.resourceKeyProvince,
    )
    const { data: districts } = useGetApi<District[]>(
        AddressConfigs.resourceUrlDistrict,
        AddressConfigs.resourceKeyDistrict,
    )
    const { data: wards } = useGetApi<Ward[]>(AddressConfigs.resourceUrlWard, AddressConfigs.resourceKeyWard)

    const handleProvinceChange = (provinceCode: string) => {
        const newFilteredDistricts = districts?.filter((district) => district.provinceCode === provinceCode) || []
        setFilteredDistricts(newFilteredDistricts)
        setFilteredWards([])
    }
    const onProvinceChange = (provinceCode: string) => {
        handleProvinceChange(provinceCode)

        form.setFieldsValue({ districtId: undefined, wardId: undefined })
    }

    const onDistrictChange = (districtCode: string) => {
        handleDistrictChange(districtCode)

        form.setFieldsValue({ wardId: undefined })
    }

    const handleDistrictChange = (districtCode: string) => {
        const newFilteredWards = wards?.filter((ward) => ward.districtCode === districtCode) || []
        setFilteredWards(newFilteredWards)
    }
    useEffect(() => {
        document.title = 'Add a new address - Vítore'
    }, [])

    const onFinish = (values: AddressRequest) => {
        const requestData: AddressRequest = {
            ...values,
            customerId: customerId || '',
        }
        createAddress(requestData, {
            onSuccess: () => {
                navigate(`${ManagerPath.CUSTOMER}/${customerId}`)
            },
        })
    }

    return {
        onFinish,
        provinces,
        filteredDistricts,
        filteredWards,
        onProvinceChange,
        onDistrictChange,
        form,
    }
}

export default useAddressCreateViewModel
