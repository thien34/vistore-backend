import { useEffect, useState } from 'react'
import { useParams, useLocation, useNavigate } from 'react-router-dom'
import useGetApi from '@/hooks/use-get-api'
import { AddressRequest, AddressResponse, District, Province, Ward } from '@/model/Address'
import AddressConfigs from './AddressConfigs'
import { Form } from 'antd'
import useUpdateApi from '@/hooks/use-update-api'
import useGetByIdApi from '@/hooks/use-get-by-id-api'
import ManagerPath from '@/constants/ManagerPath'

function useAddressUpdateViewModel() {
    const { id } = useParams<{ id: string }>()
    const location = useLocation()
    const queryParams = new URLSearchParams(location.search)
    const customerId = queryParams.get('customerId') || ''
    const [form] = Form.useForm()
    const navigate = useNavigate()
    const [filteredDistricts, setFilteredDistricts] = useState<District[]>([])
    const [filteredWards, setFilteredWards] = useState<Ward[]>([])

    const { data: provinces } = useGetApi<Province[]>(
        AddressConfigs.resourceUrlProvince,
        AddressConfigs.resourceKeyProvince,
    )
    const { data: districts } = useGetApi<District[]>(
        AddressConfigs.resourceUrlDistrict,
        AddressConfigs.resourceKeyDistrict,
    )
    const { data: wards } = useGetApi<Ward[]>(AddressConfigs.resourceUrlWard, AddressConfigs.resourceKeyWard)

    const addressIdNumber = Number(id)
    const isAddressIdValid = !isNaN(addressIdNumber)

    const { mutate: updateAddress } = useUpdateApi<AddressRequest>(
        AddressConfigs.resourceUrl,
        AddressConfigs.resourceKey,
        addressIdNumber,
    )
    const { data: currentAddress } = useGetByIdApi<AddressResponse>(
        AddressConfigs.resourceUrl,
        AddressConfigs.resourceKey,
        addressIdNumber,
    )

    useEffect(() => {
        if (isAddressIdValid && currentAddress) {
            form.setFieldsValue(currentAddress)
            handleProvinceChange(currentAddress.provinceId)

            const currentDistrict = districts?.find((district) => district.code === currentAddress.districtId)
            if (currentDistrict) {
                setFilteredDistricts((prev) => [
                    currentDistrict,
                    ...prev.filter((district) => district.code !== currentDistrict.code),
                ])
                form.setFieldsValue({ districtId: currentDistrict.code })
                handleDistrictChange(currentDistrict.code)
            } else {
                setFilteredDistricts(districts || [])
            }

            const currentWard = wards?.find((ward) => ward.code === currentAddress.wardId)
            if (currentWard) {
                setFilteredWards((prevWards) => [...prevWards, currentWard])
                form.setFieldsValue({ wardId: currentWard.code })
            }
        }
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [currentAddress, isAddressIdValid, form, wards, districts])

    const handleProvinceChange = (provinceCode: string) => {
        const newFilteredDistricts = districts?.filter((district) => district.provinceCode === provinceCode) || []

        const currentDistrict = form.getFieldValue('districtId')
        const selectedDistrict = newFilteredDistricts.find((district) => district.code === currentDistrict)

        if (selectedDistrict) {
            setFilteredDistricts([
                selectedDistrict,
                ...newFilteredDistricts.filter((district) => district.code !== currentDistrict),
            ])
        } else {
            setFilteredDistricts(newFilteredDistricts)
        }

        setFilteredWards([])
        form.setFieldsValue({ districtId: undefined, wardId: undefined })
    }
    useEffect(() => {
        document.title = 'Edit a address - Vítore'
    }, [])

    const handleDistrictChange = (districtCode: string) => {
        const newFilteredWards = wards?.filter((ward) => ward.districtCode === districtCode) || []
        setFilteredWards(newFilteredWards)

        form.setFieldsValue({ wardId: undefined })
    }

    const onFinish = (values: AddressRequest) => {
        if (isAddressIdValid) {
            const requestData: AddressRequest = {
                ...values,
                customerId: customerId,
            }
            updateAddress(requestData, {
                onSuccess: () => {
                    navigate(`${ManagerPath.CUSTOMER}/${customerId}`)
                },
            })
        } else {
            console.error('Invalid Address ID')
        }
    }

    return {
        onFinish,
        provinces,
        filteredDistricts,
        filteredWards,
        onProvinceChange: handleProvinceChange,
        onDistrictChange: handleDistrictChange,
        form,
    }
}

export default useAddressUpdateViewModel
