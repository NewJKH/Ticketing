from locust import task, FastHttpUser, events
from locust.argument_parser import LocustArgumentParser
import time

@events.init_command_line_parser.add_listener
def _(parser: LocustArgumentParser):
    parser.add_argument("--token", type=str, help="JWT for user 1")

class BookUser(FastHttpUser):
    # 각 섹터별 목표 개수
    sector_targets = {"A": 900, "B": 900, "C": 1200}
    # 현재 예매 성공 개수 (클래스 변수로 공유)
    sector_counts = {"A": 0, "B": 0, "C": 0}
    
    def on_start(self):
        self.token = self.environment.parsed_options.token
        self.current_sector = self.get_next_sector()
        self.booked = False

    def get_next_sector(self):
        # A -> B -> C 순서로 할당
        for sector in ["A", "B", "C"]:
            if self.sector_counts[sector] < self.sector_targets[sector]:
                self.sector_counts[sector] += 1
                return sector
        return None  # 모든 섹터 완료

    @task
    def book_sector(self):
        if not self.booked and self.current_sector:
            response = self.client.post(
                "/api/tickets",
                params={"sector": self.current_sector, "concertId": 1},
                headers={"Authorization": f"Bearer {self.token}"}
            )
            
        self.booked = True
        self.stop(True);