#!/usr/bin/env bash

# 쉬고 있는 profile 찾기 real1이 사용 중이면 real2가 쉬고 있음
function find_idle_profile()
{
    RESPONSE_CODE=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:profile) # 현재 nginx가  바라보는 스프링 부트가 정상적으로 수행 중인지 확인 
                                                                                     # 응답 값은 HttpStatus Code이다.
    
    if[${RESPONSE_CODE} -ge 400 ] # 400보다 크면 즉 4xx나 5xx 에러코드 발생 시
    
    then
      CURRENT_PROFILE=real2
    else
      CURRENT_PROFILE=$(curl -s http://localhost/profile)
    fi
    
    if[${CURRENT_PROFILE} == real1 ]
    then
      IDLE_PROFILE=real2                    # IDLE_PROFILE은 현재 연결되어 있지 않은 profile
    else
      IDLE_PROFILE-real1
    fi
    
    echo "${IDLE_PROFILE}"             # bash 스크립트는 값을 반환하는 기능이 없어서 제일 마지막 줄에 echo로 결과를 출력 후, 클라이언트에서 그 값을 $(함수)로 가져온다.
}

# 쉬고 있는 profile의 port 찾기
function find_idle_port()
{
    IDLE_PROFILE=$(find_idle_profile);
    
    if[ ${IDLE_PROFILE} == real1 ]
    then
      echo "8097"
    else
      echo "8098"
}
