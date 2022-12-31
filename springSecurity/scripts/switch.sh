#!/usr/bin/env bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh

function switch_proxy() {
    IDLE_PORT=$(find_idle_port)

    echo "> 전환할 Port: $IDLE_PORT"
    echo "> Port 전환"
    echo "set \$service_url http://127.0.0.1:${IDLE_PORT};" | sudo tee /etc/nginx/conf.d/service-url.inc  # echo를 이용하여 하나의 문장을 만들고
                                                                                                          # 파이프라인(|)을 이용하여 문장을 service-url.inc에 덮어씁니다.
                                                                                                          # 단 쌍따옴표를 사용해야 문장으로 인식하며 사용하지 않으면
                                                                                                          # service_url 변수를 찾게 됩니다.

    echo "> 엔진엑스 Reload"
    sudo service nginx reload             # nginx에서 변경된 설정을 다시 불러옵니다.
                                          # restart는 아예 nginx를 껏다 켜서 약간 끊기지만 reload는 끊김이 없습니다.
                                          # 다만 중요한 설정들은 restart로만 가능합니다.
                                          # 그러나 이 경우 외부 설정 파일만 다시 가져오는 것이므로 reload로 가능합니다.
}
